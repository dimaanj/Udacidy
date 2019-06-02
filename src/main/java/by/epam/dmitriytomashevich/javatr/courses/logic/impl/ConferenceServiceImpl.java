package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.ConferenceDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceDao conferenceDao = new ConferenceDao();

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
    public void deleteConferenceWithTheirContent(Long conferenceId, Long contentId) throws LogicException {
        try {
            conferenceDao.deleteConferenceWithTheirContent(conferenceId, contentId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Long countNumberOfConferences() throws LogicException {
        try {
            return conferenceDao.countNumber();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Conference> findFromRowIndexToLimit(Long rowIndex, Long limit) throws LogicException {
        try {
            return conferenceDao.findFromRowIndexToLimit(rowIndex, limit);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
