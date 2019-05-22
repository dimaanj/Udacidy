package by.epam.dmitriytomashevich.javatr.courses.command.conversation;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.MessageConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class UpdateMessagesCommand implements Command {
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final UserService userService;

    public UpdateMessagesCommand(ServiceFactory serviceFactory){
        messageService = serviceFactory.createMessageService();
        conversationService = serviceFactory.createConversationService();
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long lastMessageId = Long.parseLong(content.getParameter("lastMessageId"));
        Conversation conversation = conversationService.getByMessageId(lastMessageId);

        List<Message> messages = messageService.findAllAfterMessageId(lastMessageId, conversation.getId());
        JsonArray jsonMessagesList = new JsonArray();
        if(messages != null) {
            for (Message m : messages) {
                User creator = userService.findById(m.getCreatorId());
                m.setCreator(creator);
                JsonMessage jsonMessage = new MessageConverter().convert(m);
                Gson gson = new Gson();
                JsonElement element = gson.toJsonTree(jsonMessage, JsonMessage.class);
                jsonMessagesList.add(element);
            }
        }

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(jsonMessagesList);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
