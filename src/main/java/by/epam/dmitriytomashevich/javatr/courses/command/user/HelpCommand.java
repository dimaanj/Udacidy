package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationGroupServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConversationServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.MessageServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

public class HelpCommand implements Command {
    private final ConversationService conversationService = new ConversationServiceImpl();
    private final ConversationGroupService conversationGroupService = new ConversationGroupServiceImpl();
    private final MessageService messageService = new MessageServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User current = (User) content.getSession().getAttribute(ParameterNames.USER);
        ConversationGroup group = conversationGroupService
                .findByUserIdAndConversationType(
                        current.getId(),
                        Conversation.ConversationType.QUESTION_CONVERSATION
                );

        if (group == null) {
            Conversation conversation = new Conversation();
            conversation.setCreateDate(LocalDate.now());
            conversation.setType(Conversation.ConversationType.QUESTION_CONVERSATION);
            conversation = conversationService.createConversation(conversation);

            ConversationGroup conversationGroup = new ConversationGroup();
            conversationGroup.setUserId(current.getId());
            conversationGroup.setConversationId(conversation.getId());
            Long conversationGroupId = conversationGroupService.add(conversationGroup);
            group = conversationGroupService.findById(conversationGroupId);
        }

        content.setRequestAttribute("conversationId", group.getConversationId());
        Long messagesAmount = messageService.countMessagesByConversationId(group.getConversationId());
        if (messagesAmount > ParameterNames.MESSAGES_UPDATE_AMOUNT) {
            content.setRequestAttribute("showViewMoreButton", true);
        } else if (messagesAmount == 0) {
            content.setRequestAttribute("firstUserEnter", true);
        }
        return Optional.of(ActionNames.MESSAGES);

    }
}
