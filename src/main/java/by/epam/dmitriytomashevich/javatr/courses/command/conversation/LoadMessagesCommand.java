package by.epam.dmitriytomashevich.javatr.courses.command.conversation;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.MessageConverter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LoadMessagesCommand implements Command {
    private final MessageService messageService;
    private final UserService userService;

    public LoadMessagesCommand(ServiceFactory serviceFactory){
        messageService = serviceFactory.createMessageService();
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conversationId = Long.valueOf(content.getParameter("conversationId"));
        List<Message> messages = messageService.getSomeLastMessages(Parameter.MESSAGES_UPDATE_AMOUNT, conversationId);
        Collections.reverse(messages);
        if (messages.size() < Parameter.MESSAGES_UPDATE_AMOUNT ||
                messageService.getEarliestMessageByConversationId(conversationId).getId()
                        .equals(messages.get(0).getId())) {
            content.setRequestAttribute("hideViewMoreButton", Boolean.TRUE);
        }

        JsonArray jsonMessagesList = new JsonArray();
        for (Message m : messages) {
            User creator = userService.findById(m.getCreatorId());
            m.setCreator(creator);
            JsonMessage jsonMessage =new MessageConverter().convert(m);
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(jsonMessage, JsonMessage.class);
            jsonMessagesList.add(element);
        }
        boolean hideViewMoreButton = false;
        if (messages.size() < Parameter.MESSAGES_UPDATE_AMOUNT ||
                messageService.getEarliestMessageByConversationId(conversationId).getId()
                        .equals(messages.get(0).getId())) {
            hideViewMoreButton = true;
        }

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.putPOJO("messages", jsonMessagesList);
            node.put("hideViewMoreButton", hideViewMoreButton);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(jsonMessagesList);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
