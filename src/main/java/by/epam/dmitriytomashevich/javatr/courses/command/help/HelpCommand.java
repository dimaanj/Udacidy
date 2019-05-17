package by.epam.dmitriytomashevich.javatr.courses.command.help;

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

import java.time.LocalDate;
import java.util.Optional;

public class HelpCommand implements Command {
    private static final ConversationService CONVERSATION_SERVICE = new ConversationServiceImpl();
    private static final ConversationGroupService CONVERSATION_GROUP_SERVICE = new ConversationGroupServiceImpl();
    private static final MessageService MESSAGE_SERVICE = new MessageServiceImpl();


    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User current = (User) content.getSession().getAttribute(Parameter.USER);
        Conversation conversation;
        if (!CONVERSATION_SERVICE.isQuestionConversationCreatedForUser(current)) {
            conversation = new Conversation();
            conversation.setCreateDate(LocalDate.now());
            conversation.setType(Conversation.ConversationType.QUESTION_CONVERSATION);
            conversation = CONVERSATION_SERVICE.createConversation(conversation);
            ConversationGroup conversationGroup =
                    CONVERSATION_GROUP_SERVICE.defineConversationGroup(current.getId(), conversation.getId());
            CONVERSATION_GROUP_SERVICE.add(conversationGroup);
        } else {
            conversation = CONVERSATION_SERVICE.getSingleQuestionConversationForUser(current);
        }
        content.setRequestAttribute("conversationId", conversation.getId());
        Long messagesAmount = MESSAGE_SERVICE.countMessagesByConversationId(conversation.getId());
        if(messagesAmount > Parameter.MESSAGES_UPDATE_AMOUNT){
            content.setRequestAttribute("showViewMoreButton", true);
        } else if(messagesAmount == 0){
            content.setRequestAttribute("firstUserEnter", true);
        }
        return Optional.of(JSP.MESSAGES);
    }
}
