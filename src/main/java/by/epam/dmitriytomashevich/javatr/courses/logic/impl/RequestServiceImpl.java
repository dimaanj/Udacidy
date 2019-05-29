package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.RequestDao;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestStatus;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;

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
    public List<Request> findByUserId(Long userId) throws LogicException {
        try {
            return requestDao.findByUserId(userId);
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
    public Request findByUserIdAndConferenceId(Long userId, Long conferenceId) throws LogicException {
        try {
            return requestDao.findByUserIdAndConferenceId(userId, conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Request> findByConferenceId(Long conferenceId) throws LogicException {
        try {
            return requestDao.findByConferenceId(conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Request> findAll() throws LogicException {
        try {
            return requestDao.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void updateRequestStatusByUserIdAndConferenceId(Long userId, Long conferenceId, RequestStatus requestStatus) throws LogicException {
        try {
            requestDao.updateRequestStatusByUserIdAndConferenceId(userId, conferenceId, requestStatus);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
