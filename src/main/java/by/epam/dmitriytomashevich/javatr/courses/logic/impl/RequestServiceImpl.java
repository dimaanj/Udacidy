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
    public void deleteRequestWithRequestData(Long requestId) throws LogicException {
        try {
            requestDao.deleteRequestWithRequestData(requestId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Request findByUserIdAndConferenceId(Long userId, Long conferenceId) throws LogicException {
        try {
            return requestDao.findRequestByUserIdAndConferenceId(userId, conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
