package by.epam.dmitriytomashevich.javatr.courses.command.conversation;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.MessageServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class UpdateMessagesCommand implements Command {
    private static final MessageService MESSAGE_SERVICE = new MessageServiceImpl();
    private static final ConversationService CONVERSATION_SERVICE = new ConversationServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long lastMessageId = Long.parseLong(content.getParameter("lastMessageId"));
        Conversation conversation = CONVERSATION_SERVICE.getByMessageId(lastMessageId);

        List<Message> messages = MESSAGE_SERVICE.findAllAfterMessageId(lastMessageId, conversation.getId());
        JsonArray jsonMessagesList = new JsonArray();

        for (Message m : messages) {
            User creator = USER_SERVICE.findById(m.getCreatorId());
            m.setCreator(creator);
            JsonMessage jsonMessage = MESSAGE_SERVICE.toJsonMessage(m);
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(jsonMessage, JsonMessage.class);
            jsonMessagesList.add(element);
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
