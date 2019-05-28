package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public interface ConversationGroupService {
    Long add(ConversationGroup conversationGroup) throws LogicException;
    void deleteByConversationId(Long conversationId) throws LogicException;
    ConversationGroup findByUserIdAndConversationType(Long userId, Conversation.ConversationType type) throws LogicException;
    ConversationGroup findById(Long id) throws LogicException;
}
