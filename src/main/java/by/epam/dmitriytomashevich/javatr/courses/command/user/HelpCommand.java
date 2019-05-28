package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.time.LocalDate;
import java.util.Optional;

public class HelpCommand implements Command {
    private final ConversationService conversationService;
    private final ConversationGroupService conversationGroupService;
    private final MessageService messageService;


    public HelpCommand(ServiceFactory serviceFactory){
        conversationService = serviceFactory.createConversationService();
        conversationGroupService = serviceFactory.createConversationGroupService();
        messageService = serviceFactory.createMessageService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User current = (User) content.getSession().getAttribute(Parameter.USER);
        ConversationGroup group = conversationGroupService
                .findByUserIdAndConversationType(
                        current.getId(),
                        Conversation.ConversationType.QUESTION_CONVERSATION
                );
        if(content.getActionType().equals(SessionRequestContent.ActionType.REDIRECT)) {
            if (group == null) {
                Conversation conversation = new Conversation();
                conversation.setCreateDate(LocalDate.now());
                conversation.setType(Conversation.ConversationType.QUESTION_CONVERSATION);
                conversation = conversationService.createConversation(conversation);

                ConversationGroup conversationGroup = new ConversationGroup();
                conversationGroup.setUserId(current.getId());
                conversationGroup.setConversationId(conversation.getId());
                conversationGroupService.add(conversationGroup);
            }
            return Optional.of(ActionNames.HELP_ACTION);
        }else {
            content.setRequestAttribute("conversationId", group.getConversationId());
            Long messagesAmount = messageService.countMessagesByConversationId(group.getConversationId());
            if (messagesAmount > Parameter.MESSAGES_UPDATE_AMOUNT) {
                content.setRequestAttribute("showViewMoreButton", true);
            } else if (messagesAmount == 0) {
                content.setRequestAttribute("firstUserEnter", true);
            }
            return Optional.of(ActionNames.MESSAGES);
        }
    }
}
