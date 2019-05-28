package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.ConversationDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public class ConversationServiceImpl implements ConversationService {
    private final ConversationDao conversationDao;

    public ConversationServiceImpl(DaoFactory daoFactory){
        this.conversationDao = daoFactory.createConversationDao();
    }

    @Override
    public List<Conversation> getAllConversations() throws LogicException {
        try {
            return conversationDao.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Conversation getById(Long id) throws LogicException {
        try {
            return conversationDao.findById(id);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new LogicException(e);
        }
    }

    @Override
    public Conversation getSingleQuestionConversationForUser(User user) throws LogicException {
        try {
            return conversationDao.findUserConversationByType(user.getId(),
                    Conversation.ConversationType.QUESTION_CONVERSATION);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public boolean isQuestionConversationCreatedForUser(User user) throws LogicException {
        try {
            return conversationDao.findUserConversationByType(user.getId(),
                    Conversation.ConversationType.QUESTION_CONVERSATION) != null;
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public boolean isUserInConversation(User user, Long conversationId) throws LogicException {
        try {
            List<Conversation> conversations = conversationDao.findAllByUserId(user.getId());
            for(Conversation c : conversations){
                if(c != null && c.getId().equals(conversationId)){
                    return true;
                }
            }
            return false;
        } catch (DAOException e) {
            e.printStackTrace();
            throw new LogicException(e);
        }

    }

    @Override
    public List<Conversation> findAllConversationByType(Conversation.ConversationType type) throws LogicException {
        try {
            return conversationDao.findAllConversationsByType(type);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws LogicException {
        try {
            conversationDao.deleteById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Conversation createConversation(Conversation conversation) throws LogicException {
        try {
            //Conversation group = new Conversation();
           // group.setCreateDate();
            Long conversationId = conversationDao.create(conversation);
            conversation.setId(conversationId);
            return conversation;
        } catch (DAOException e) {
            e.printStackTrace();
            throw new LogicException(e);
        }
    }

    @Override
    public Conversation getByMessageId(Long messageId) throws LogicException {
        try {
            return conversationDao.findByMessageId(messageId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public boolean isConversationWithAdminsCreated(){
        return false;
    }
}
