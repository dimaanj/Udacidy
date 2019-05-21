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


    /**
     * This methods works only for user. Not for admins.
     * Because user have only one question conversation.
     * @param user
     * @return
     * @throws LogicException
     */
    Conversation getSingleQuestionConversationForUser(User user) throws LogicException;
    boolean isQuestionConversationCreatedForUser(User user) throws LogicException;
}
