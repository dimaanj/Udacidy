package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.RequestDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public class RequestServiceImpl implements RequestService {
    private final RequestDao requestDao;

    public RequestServiceImpl(DaoFactory daoFactory){
        requestDao = daoFactory.createRequestDao();
    }

    @Override
    public Long create(Request request) throws LogicException {
        try {
            return requestDao.create(request);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Request findBySectionIdAndUserId(Long sectionId, Long userId) throws LogicException {
        try {
            return requestDao.findBySectionIdAndUserId(sectionId, userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Request> findBySectionId(Long sectionId) throws LogicException {
        try {
            return requestDao.findBySectionId(sectionId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long requestId) throws LogicException {
        try {
            requestDao.deleteById(requestId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Request> findAllByUserIdAndConferenceId(Long userId, Long conferenceId) throws LogicException {
        try {
            return requestDao.findAllRequestByUserIdAndConferenceId(userId, conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Request> findAllByUserId(Long userId) throws LogicException {
        try {
            return requestDao.findAllByUserId(userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Request findById(Long requestId) throws LogicException {
        try {
            return requestDao.findById(requestId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void deleteFullRequestByConferenceIdAndUserId(Long conferenceId, Long userId) throws LogicException {
        try {
            requestDao.deleteFullRequestByConferenceId(conferenceId, userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
