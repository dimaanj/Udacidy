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
        Conversation conversation;
        if (!conversationService.isQuestionConversationCreatedForUser(current)) {
            conversation = new Conversation();
            conversation.setCreateDate(LocalDate.now());
            conversation.setType(Conversation.ConversationType.QUESTION_CONVERSATION);
            conversation = conversationService.createConversation(conversation);
            ConversationGroup conversationGroup =
                    conversationGroupService.defineConversationGroup(current.getId(), conversation.getId());
            conversationGroupService.add(conversationGroup);
        } else {
            conversation = conversationService.getSingleQuestionConversationForUser(current);
        }
        content.setRequestAttribute("conversationId", conversation.getId());
        Long messagesAmount = messageService.countMessagesByConversationId(conversation.getId());
        if(messagesAmount > Parameter.MESSAGES_UPDATE_AMOUNT){
            content.setRequestAttribute("showViewMoreButton", true);
        } else if(messagesAmount == 0){
            content.setRequestAttribute("firstUserEnter", true);
        }
        return Optional.of(ActionNames.MESSAGES);
    }
}
