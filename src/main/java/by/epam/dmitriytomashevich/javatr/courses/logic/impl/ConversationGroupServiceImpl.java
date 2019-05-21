package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.ConversationGroupDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public class ConversationGroupServiceImpl implements ConversationGroupService {
    private final ConversationGroupDao conversationGroupDao;

    public ConversationGroupServiceImpl(DaoFactory daoFactory){
        conversationGroupDao = daoFactory.createConversationGroupDao();
    }

    @Override
    public Long add(ConversationGroup conversationGroup) throws LogicException {
        try {
            return conversationGroupDao.create(conversationGroup);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public ConversationGroup defineConversationGroup(Long userId, Long conversationId) {
        ConversationGroup conversationGroup = new ConversationGroup();
        conversationGroup.setUserId(userId);
        conversationGroup.setConversationId(conversationId);
        return conversationGroup;
    }
}
