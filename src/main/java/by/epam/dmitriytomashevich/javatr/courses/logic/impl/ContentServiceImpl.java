package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.ContentDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public class ContentServiceImpl implements ContentService {
    private final ContentDao contentDao;

    public ContentServiceImpl(DaoFactory daoFactory){
        contentDao = daoFactory.createContentDao();
    }

    @Override
    public Long create(Content content) throws LogicException {
        try {
            return contentDao.create(content);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Content findById(Long id) throws LogicException {
        try {
            return contentDao.findById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Content findByConferenceId(Long conferenceId) throws LogicException {
        try {
            return contentDao.findByConferenceId(conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

    }

    @Override
    public void update(Content content) throws LogicException {
        try {
            contentDao.update(content);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long id) throws LogicException {
        try {
            contentDao.deleteById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Content findBySectionId(Long sectionId) throws LogicException {
        try {
            return contentDao.findBySectionId(sectionId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
