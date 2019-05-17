package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationGroupServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.MessageServiceImpl;

import java.util.Optional;

public class AdminConversationCommand implements Command {
    private static final ConversationGroupService CONVERSATION_GROUP_SERVICE = new ConversationGroupServiceImpl();
    private static final ConversationService CONVERSATION_SERVICE = new ConversationServiceImpl();
    private static final MessageService MESSAGE_SERVICE = new MessageServiceImpl();

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
            content.setRequestAttribute("conversationId", conversation.getId());
            Long messagesAmount = MESSAGE_SERVICE.countMessagesByConversationId(conversationId);
            if(messagesAmount > Parameter.MESSAGES_UPDATE_AMOUNT){
                content.setRequestAttribute("showViewMoreButton", true);
            }
            return Optional.of(JSP.MESSAGES);
        }
    }
}
