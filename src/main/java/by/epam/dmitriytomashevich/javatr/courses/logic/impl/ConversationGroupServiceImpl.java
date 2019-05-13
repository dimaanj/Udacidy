package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.ConversationGroupDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public class ConversationGroupServiceImpl implements ConversationGroupService {
    private static final ConversationGroupDao CONVERSATION_GROUP_DAO = new ConversationGroupDao();

    @Override
    public Long add(ConversationGroup conversationGroup) throws LogicException {
        try {
            return CONVERSATION_GROUP_DAO.create(conversationGroup);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public boolean isConversationGroupCreatedForUser(User user) {
        return false;
    }

    @Override
    public ConversationGroup defineConversationGroup(Long userId, Long conversationId) {
        ConversationGroup conversationGroup = new ConversationGroup();
        conversationGroup.setUserId(userId);
        conversationGroup.setConversationId(conversationId);
        return conversationGroup;
    }

//    @Override
//    public boolean isUserInConversationGroup(User user, Long conversationId) throws LoadException {
//        try {
//            ConversationGroup groupOne = CONVERSATION_GROUP_DAO.findByConversationId(conversationId);
//            Conversation groupTwo = CONVERSATION_DAO.findByUserId(user.getId());
//            return groupTwo != null && groupOne.getConversationId().equals(groupTwo.getId());
//        } catch (DAOException e) {
//            throw new LoadException();
//        }
//    }
}
