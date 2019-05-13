package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.MessageDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import javafx.fxml.LoadException;

import java.time.LocalDateTime;
import java.util.List;

public class MessageServiceImpl implements MessageService {
    private static final MessageDao MESSAGE_DAO = new MessageDao();
    private static final UserService USER_SERVICE = new UserServiceImpl();


    @Override
    public List<Message> findChatMessagesByConversationId(Long id) throws LogicException {
        try {
            return MESSAGE_DAO.findByConversationId(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Long add(Message message) throws LogicException {
        try {
            return MESSAGE_DAO.create(message);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new LogicException(e);
        }
    }

    @Override
    public Message getLastOnTimeByConversationId(Long id) throws LogicException {
        try {
            return MESSAGE_DAO.findLastOnTimeByConversationId(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Message> getMessagesByConversationId(Long conversationId) throws LogicException {
        List<Message> messages = findChatMessagesByConversationId(conversationId);

        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            User author = USER_SERVICE.findById(m.getCreatorId());
            m.setCreator(author);
            messages.set(i, m);
        }
        return messages;
    }

    @Override
    public List<Message> getSomeLastMessages(int amount, Long conversationId) throws LogicException {
        try {
            List<Message> messages = MESSAGE_DAO.findSomeLastByConversationId(amount, conversationId);
            for (Message m : messages){
                User author = USER_SERVICE.findById(m.getCreatorId());
                m.setCreator(author);
            }
            return messages;
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Message> findSomeLastByConversationIdStartsWithMessageId(int amount, Long conversationId, Long messageId) throws LogicException {
        try {
            return MESSAGE_DAO.findSomeLastByConversationIdStartsWithMessageId(amount, conversationId, messageId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Message> findAllAfterMessageId(Long messageId, Long conversationId) throws LogicException {
        try {
            return MESSAGE_DAO.findAllAfterMessageId(messageId, conversationId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Message getEarliestMessageByConversationId(Long conversationId) throws LogicException {
        try {
            return MESSAGE_DAO.findEarliestMessageByConversationId(conversationId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void updateMessageImage(String imagePath, Long messageId) throws LogicException {
        try {
            MESSAGE_DAO.updateMessageImagePath(imagePath, messageId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public JsonMessage toJsonMessage(Message message) {
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setId(message.getId());
        jsonMessage.setFormattedCreationDateTime(message.getFormattedCreationDateTime());
        jsonMessage.setCreatorFirstName(message.getCreator().getFirstName());
        jsonMessage.setCreatorLastName(message.getCreator().getLastName());
        jsonMessage.setText(message.getText());
        jsonMessage.setCreatorId(message.getCreator().getId());
        jsonMessage.setCreatorRole(message.getCreator().getRole());
        jsonMessage.setConversationId(message.getConversationId());
        jsonMessage.setImageUrl(message.getImageUrl());
        return jsonMessage;
    }
}
