package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.Optional;

public class AdminConversationCommand implements Command {
    private final ConversationGroupService conversationGroupService;
    private final ConversationService conversationService;
    private final MessageService messageService;

    public AdminConversationCommand(ServiceFactory serviceFactory){
        conversationGroupService = serviceFactory.createConversationGroupService();
        conversationService = serviceFactory.createConversationService();
        messageService = serviceFactory.createMessageService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {

        Long conversationId = Long.parseLong(content.getParameter("conversationId"));
        if (content.getActionType().equals(SessionRequestContent.ActionType.REDIRECT)) {
            User admin = (User) content.getSession().getAttribute(Parameter.USER);
            if (!conversationService.isUserInConversation(admin, conversationId)) {
                ConversationGroup group = new ConversationGroup();
                group.setConversationId(conversationId);
                group.setUserId(admin.getId());
                conversationGroupService.add(group);
            }
            return Optional.of(JSP.ADMIN_CONVERSATION_ACTION + conversationId);
        } else {
            Conversation conversation = conversationService.getById(conversationId);
            content.setRequestAttribute("conversationId", conversation.getId());
            Long messagesAmount = messageService.countMessagesByConversationId(conversationId);
            if(messagesAmount > Parameter.MESSAGES_UPDATE_AMOUNT){
                content.setRequestAttribute("showViewMoreButton", true);
            }
            return Optional.of(JSP.MESSAGES);
        }
    }
}
