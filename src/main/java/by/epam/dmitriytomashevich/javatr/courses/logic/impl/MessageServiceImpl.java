package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.MessageDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonMessage;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.MessageService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;
    private final UserService userService;

    public MessageServiceImpl(DaoFactory daoFactory){
        messageDao = daoFactory.createMessageDao();
        userService = new UserServiceImpl(daoFactory);
    }

    @Override
    public Long create(Message message) throws LogicException {
        try {
            return messageDao.create(message);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new LogicException(e);
        }
    }

    @Override
    public Message getLastOnTimeByConversationId(Long id) throws LogicException {
        try {
            return messageDao.findLastOnTimeByConversationId(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Message> getSomeLastMessages(int amount, Long conversationId) throws LogicException {
        try {
            List<Message> messages = messageDao.findSomeLastByConversationId(amount, conversationId);
            for (Message m : messages){
                User author = userService.findById(m.getCreatorId());
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
            return messageDao.findSomeLastByConversationIdStartsWithMessageId(amount, conversationId, messageId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Message> findAllAfterMessageId(Long messageId, Long conversationId) throws LogicException {
        try {
            return messageDao.findAllAfterMessageId(messageId, conversationId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Message getEarliestMessageByConversationId(Long conversationId) throws LogicException {
        try {
            return messageDao.findEarliestMessageByConversationId(conversationId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void updateMessageImage(String imagePath, Long messageId) throws LogicException {
        try {
            messageDao.updateMessageImagePath(imagePath, messageId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Long countMessagesByConversationId(Long conversationId) throws LogicException {
        try {
            return messageDao.countMessagesByConferenceId(conversationId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void removeAllByConversationId(long conversationId) throws LogicException {
        try {
            messageDao.removeAllByConversationId(conversationId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Message> findAllByConversationId(Long conversationId) throws LogicException {
        try {
            return messageDao.findAllByConversationId(conversationId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
