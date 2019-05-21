package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public interface ConversationGroupService {
    Long add(ConversationGroup conversationGroup) throws LogicException;
    ConversationGroup defineConversationGroup(Long userId, Long conversationId);
}
