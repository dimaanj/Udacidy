package by.epam.dmitriytomashevich.javatr.courses.command.conversation;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class SendMessageCommand implements Command {
    private final ConversationService conversationService;
    private final MessageService messageService;

    public SendMessageCommand(ServiceFactory serviceFactory){
        conversationService = serviceFactory.createConversationService();
        messageService = serviceFactory.createMessageService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        Long conversationId = Long.parseLong(content.getParameter("conversationId"));
        Conversation conversation = conversationService.getById(conversationId);
        String text = content.getParameter("message");

        Optional<StringBuilder> fileName = uploadToServer(content.getRequest());
        String imageServerPath = null;
        if (fileName.isPresent()) {
            imageServerPath = ".." + File.separator + "images" + File.separator + "tmp" + File.separator + fileName.get();
        }
        Message message = createMessage(user, text, conversation, imageServerPath);
        Long messageId = messageService.add(message);

        fileName.ifPresent(stringBuilder -> new Thread(() -> {
            String cloudImageUrl = addToCloud(
                    (content.getRequest().getServletContext().getRealPath("")
                            + File.separator + "images" + File.separator + "tmp" + File.separator + stringBuilder)
            );
            try {
                messageService.updateMessageImage(cloudImageUrl, messageId);
            } catch (LogicException e) {
                e.printStackTrace();
            }
        }).start());

        message.setId(messageId);
        JsonMessage jsonMessage = messageService.toJsonMessage(message);
        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(jsonMessage);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(json);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }

    private Optional<StringBuilder> uploadToServer(HttpServletRequest request) throws LogicException {
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + "images" + File.separator + "tmp";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            String fileName = (LocalDateTime.now().toString() + ".png").replaceAll(":", "-");
            StringBuilder finalName = new StringBuilder(fileName);
            for (Part part : request.getParts()) {
                if (part.getName().equals("file") && part.getSubmittedFileName() != null) {
                    part.write(uploadPath + File.separator + fileName);
                    return Optional.of(finalName);
                }
            }
            return Optional.empty();
        } catch (IOException | ServletException e) {
            throw new LogicException(e);
        }
    }

    private String addToCloud(String fullPath) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dtpngtrki",
                "api_key", "684288839384791",
                "api_secret", "tq27mZs8v4Hd4Uon-t5O4BJ10gQ"));
        Map uploadRezult = null;
        try {
            uploadRezult = cloudinary.uploader().upload(fullPath, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadRezult.get("secure_url").toString();
    }

    private Message createMessage(User user, String text, Conversation conversation, String imageUrl) {
        Message message = new Message();
        message.setCreatorId(user.getId());
        message.setCreator(user);
        message.setText(text);
        message.setCreationDateTime(LocalDateTime.now());
        message.setConversationId(conversation.getId());
        message.setImageUrl(imageUrl);
        return message;
    }
}
