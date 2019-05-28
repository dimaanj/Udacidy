package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface ConversationService {
    List<Conversation> getAllConversations() throws LogicException;
    Conversation getById(Long id) throws LogicException;
    Conversation createConversation(Conversation conversation) throws LogicException;
    Conversation getByMessageId(Long messageId) throws LogicException;
    boolean isUserInConversation(User user, Long conversationId) throws LogicException;
    List<Conversation> findAllByType(Conversation.ConversationType type) throws LogicException;
    void deleteById(Long id) throws LogicException;
}