package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public interface MessageService {
    List<Message> findChatMessagesByConversationId(Long id) throws LogicException;
    Long add(Message message) throws LogicException;
    Message getLastOnTimeByConversationId(Long id) throws LogicException;
    List<Message> getMessagesByConversationId(Long conversationId) throws LogicException;
    List<Message> getSomeLastMessages(int amount, Long conversationId) throws LogicException;
    List<Message> findSomeLastByConversationIdStartsWithMessageId(int amount, Long conversationId, Long messageId) throws LogicException;
    List<Message> findAllAfterMessageId(Long messageId, Long conversationId) throws LogicException;
    Message getEarliestMessageByConversationId(Long conversationId) throws LogicException;
    void updateMessageImage(String imagePath, Long messageId) throws LogicException;
    Long countMessagesByConversationId(Long conversationId) throws LogicException;

    // todo вынести
    JsonMessage toJsonMessage(Message message);
}
