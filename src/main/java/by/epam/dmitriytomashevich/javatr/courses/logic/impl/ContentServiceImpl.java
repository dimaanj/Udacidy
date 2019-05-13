package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.ContentDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public class ContentServiceImpl implements ContentService {
    private static final ContentDao CONTENT_DAO = new ContentDao();

    @Override
    public Long create(Content content) throws LogicException {
        try {
            return CONTENT_DAO.create(content);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Content findById(Long id) throws LogicException {
        try {
            return CONTENT_DAO.findById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Content findByConferenceId(Long conferenceId) throws LogicException {
        try {
            return CONTENT_DAO.findByConferenceId(conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

    }

    @Override
    public void update(Content content) throws LogicException {
        try {
            CONTENT_DAO.update(content);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long id) throws LogicException {
        try {
            CONTENT_DAO.deleteById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Content findBySectionId(Long sectionId) throws LogicException {
        try {
            return CONTENT_DAO.findBySectionId(sectionId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
