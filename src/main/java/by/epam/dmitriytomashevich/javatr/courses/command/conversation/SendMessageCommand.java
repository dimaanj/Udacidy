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
import by.epam.dmitriytomashevich.javatr.courses.util.converter.MessageConverter;
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

        Optional<String> fileName = uploadToServer(content.getRequest());
        String imageServerPath = null;
        if (fileName.isPresent()) {
            imageServerPath = ".." + File.separator + "images" + File.separator + "tmp" + File.separator + fileName.get();
        }
        Message message = createMessage(user, text, conversation, imageServerPath);
        Long messageId = messageService.create(message);
        uploadToCloud(content, fileName, messageId);

        message.setId(messageId);
        JsonMessage jsonMessage = new MessageConverter().convert(message);
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

    private Optional<String> uploadToServer(HttpServletRequest request) throws LogicException {
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + "images" + File.separator + "tmp";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            String fileName = (LocalDateTime.now().toString() + ".png").replaceAll(":", "-");
            for (Part part : request.getParts()) {
                if (part.getName().equals("file") && part.getSubmittedFileName() != null) {
                    part.write(uploadPath + File.separator + fileName);
                    return Optional.of(fileName);
                }
            }
            return Optional.empty();
        } catch (IOException | ServletException e) {
            throw new LogicException(e);
        }
    }

    private void uploadToCloud(SessionRequestContent content, Optional<String> optionalFileName, Long messageId){
        optionalFileName.ifPresent(fileName -> new Thread(() -> {
            String fullPath =
                    content.getRequest().getServletContext().getRealPath("")
                            + File.separator + "images" + File.separator + "tmp" + File.separator + fileName;

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dtpngtrki",
                    "api_key", "684288839384791",
                    "api_secret", "tq27mZs8v4Hd4Uon-t5O4BJ10gQ"));

            try {
                Map uploadRezult = cloudinary.uploader().upload(fullPath, ObjectUtils.emptyMap());
                String cloudImageUrl = uploadRezult.get("secure_url").toString();
                messageService.updateMessageImage(cloudImageUrl, messageId);
            } catch (IOException | LogicException e) {
                e.printStackTrace();
            }
        }).start());
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
