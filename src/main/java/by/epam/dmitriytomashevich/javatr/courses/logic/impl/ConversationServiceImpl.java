package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.ConversationDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import javafx.fxml.LoadException;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

public class ConversationServiceImpl implements ConversationService {
    private static final ConversationDao CONVERSATION_DAO = new ConversationDao();

    @Override
    public List<Conversation> getAllConversations() throws LogicException {
        try {
            return CONVERSATION_DAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Conversation getById(Long id) throws LogicException {
        try {
            return CONVERSATION_DAO.findById(id);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new LogicException(e);
        }
    }

    @Override
    public Conversation getSingleQuestionConversationForUser(User user) throws LogicException {
        try {
            return CONVERSATION_DAO.findUserConversationByType(user.getId(),
                    Conversation.ConversationType.QUESTION_CONVERSATION);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public boolean isQuestionConversationCreatedForUser(User user) throws LogicException {
        try {
            return CONVERSATION_DAO.findUserConversationByType(user.getId(),
                    Conversation.ConversationType.QUESTION_CONVERSATION) != null;
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public boolean isUserInConversation(User user, Long conversationId) throws LogicException {
        try {
            List<Conversation> conversations = CONVERSATION_DAO.findAllByUserId(user.getId());
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
    public Conversation createConversation(Conversation conversation) throws LogicException {
        try {
            //Conversation group = new Conversation();
           // group.setCreateDate();
            Long conversationId = CONVERSATION_DAO.create(conversation);
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
            return CONVERSATION_DAO.findByMessageId(messageId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    public boolean isConversationWithAdminsCreated(){
        return false;
    }
}
