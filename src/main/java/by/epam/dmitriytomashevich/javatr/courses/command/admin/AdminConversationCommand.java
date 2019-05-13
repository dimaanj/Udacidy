package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationGroupServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.MessageServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdminConversationCommand implements Command {
    private static final ConversationGroupService CONVERSATION_GROUP_SERVICE = new ConversationGroupServiceImpl();
    private static final ConversationService CONVERSATION_SERVICE = new ConversationServiceImpl();
    private static final MessageService MESSAGE_SERVICE = new MessageServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {

        Long conversationId = Long.parseLong(content.getParameter("conversationId"));
        if (content.getActionType().equals(SessionRequestContent.ActionType.REDIRECT)) {
            User admin = (User) content.getSession().getAttribute(Parameter.USER);
            if (!CONVERSATION_SERVICE.isUserInConversation(admin, conversationId)) {
                ConversationGroup group = new ConversationGroup();
                group.setConversationId(conversationId);
                group.setUserId(admin.getId());
                CONVERSATION_GROUP_SERVICE.add(group);
            }
            return Optional.of(JSP.ADMIN_CONVERSATION_ACTION + conversationId);
        } else {
            Conversation conversation = CONVERSATION_SERVICE.getById(conversationId);
            List<Message> messages = MESSAGE_SERVICE.getSomeLastMessages(Parameter.MESSAGES_UPDATE_AMOUNT, conversation.getId());
            Collections.reverse(messages);
            if(messages.size() < Parameter.MESSAGES_UPDATE_AMOUNT ||
                    MESSAGE_SERVICE.getEarliestMessageByConversationId(conversation.getId()).getId()
                            .equals(messages.get(0).getId())){
                content.setRequestAttribute("hideViewMoreButton", Boolean.TRUE);
            }

            JsonArray jsonMessagesList = new JsonArray();
            for (Message m : messages) {
                User creator = USER_SERVICE.findById(m.getCreatorId());
                m.setCreator(creator);
                JsonMessage jsonMessage = MESSAGE_SERVICE.toJsonMessage(m);
                Gson gson = new Gson();
                JsonElement element = gson.toJsonTree(jsonMessage, JsonMessage.class);
                jsonMessagesList.add(element);
            }
            if(!messages.isEmpty()){
                content.setRequestAttribute("messages", jsonMessagesList);
            }
            if(messages.size() < Parameter.MESSAGES_UPDATE_AMOUNT ||
                    MESSAGE_SERVICE.getEarliestMessageByConversationId(conversation.getId()).getId()
                            .equals(messages.get(0).getId())){
                content.setRequestAttribute("hideViewMoreButton", Boolean.TRUE);
            }
            content.setRequestAttribute("conversationId", conversation.getId());
            return Optional.of(JSP.MESSAGES);
        }
    }


}
