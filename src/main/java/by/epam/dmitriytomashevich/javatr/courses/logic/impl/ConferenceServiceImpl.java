package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.ConferenceDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public class ConferenceServiceImpl implements ConferenceService {
    private static final ConferenceDao CONFERENCE_DAO = new ConferenceDao();

    @Override
    public Long create(Conference conference) throws LogicException {
        try {
            return CONFERENCE_DAO.create(conference);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Conference> findSomeLastConferences(int amount) throws LogicException {
        try {
            return CONFERENCE_DAO.findSomeLastConferences(amount);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Conference getById(Long id) throws LogicException {
        try {
            return CONFERENCE_DAO.findById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long id) throws LogicException {
        try {
            CONFERENCE_DAO.deleteById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Conference getTheOldest() throws LogicException {
        try {
            return CONFERENCE_DAO.findTheOldest();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Conference> findSomeOlderStartsWithConversationId(Long id) throws LogicException {
        try {
            return CONFERENCE_DAO.findSomeOlderStartsWithConferenceId(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void deleteConferenceWithTheirContent(Long conferenceId, Long contentId) throws LogicException {
        try {
            CONFERENCE_DAO.deleteConferenceWithTheirContent(conferenceId, contentId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
