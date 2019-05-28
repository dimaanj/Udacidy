package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.ConferenceDao;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceDao conferenceDao;

    public ConferenceServiceImpl(DaoFactory daoFactory){
        conferenceDao = daoFactory.createConferenceDao();
    }

    @Override
    public Long create(Conference conference) throws LogicException {
        try {
            return conferenceDao.create(conference);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Conference> findSomeLastConferences(int amount) throws LogicException {
        try {
            return conferenceDao.findSomeLastConferences(amount);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Conference getById(Long id) throws LogicException {
        try {
            return conferenceDao.findById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long id) throws LogicException {
        try {
            conferenceDao.deleteById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Conference getTheOldest() throws LogicException {
        try {
            return conferenceDao.findTheOldest();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Conference> findSomeOlderStartsWithConversationId(Long id) throws LogicException {
        try {
            return conferenceDao.findSomeOlderStartsWithConferenceId(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void deleteConferenceWithTheirContent(Long conferenceId, Long contentId) throws LogicException {
        try {
            conferenceDao.deleteConferenceWithTheirContent(conferenceId, contentId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Conference> findAllConferencesAsUserRequestsByUserId(Long userId) throws LogicException {
        try {
            return conferenceDao.findAllConferencesAsUserRequestsByUserId(userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Conference> findAllConferencesAsUserRequests() throws LogicException {
        try {
            return conferenceDao.findAllConferencesAsUserRequests();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
