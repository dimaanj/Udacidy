package by.epam.dmitriytomashevich.javatr.courses.command.conversation;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.MessageConverter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class ViewMoreCommand implements Command {
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final UserService userService;

    public ViewMoreCommand(ServiceFactory serviceFactory){
        messageService = serviceFactory.createMessageService();
        conversationService = serviceFactory.createConversationService();
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long earliestDisplayedMessageId = Long.parseLong(content.getParameter("earliestMessageId"));
        Conversation conversation = conversationService.getByMessageId(earliestDisplayedMessageId);
        List<Message> messages = messageService.findSomeLastByConversationIdStartsWithMessageId(
                ParameterNames.MESSAGES_UPDATE_AMOUNT, conversation.getId(), earliestDisplayedMessageId);

        JsonArray jsonMessagesList = new JsonArray();
        for (Message m : messages) {
            User creator = userService.findById(m.getCreatorId());
            m.setCreator(creator);
            JsonMessage jsonMessage = new MessageConverter().convert(m);
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(jsonMessage, JsonMessage.class);
            jsonMessagesList.add(element);
        }

        content.getResponse().setContentType("application/json;charset=UTF-8");
        boolean hideViewMoreButton = false;
        if(messages.size() < ParameterNames.MESSAGES_UPDATE_AMOUNT ||
                messageService.getEarliestMessageByConversationId(conversation.getId()).getId()
                        .equals(messages.get(messages.size()-1).getId())){
            hideViewMoreButton = true;
        }
        try {
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.putPOJO("messages", jsonMessagesList);
            node.put("hideViewMoreButton", hideViewMoreButton);

            PrintWriter writer = content.getResponse().getWriter();

            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
