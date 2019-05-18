package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.RequestDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public class RequestServiceImpl implements RequestService {
    private static final RequestDao REQUEST_DAO = new RequestDao();

    @Override
    public Long create(Request request) throws LogicException {
        try {
            return REQUEST_DAO.create(request);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long id) throws LogicException {
        try {
            REQUEST_DAO.deleteById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Request findBySectionIdAndUserId(Long sectionId, Long userId) throws LogicException {
        try {
            return REQUEST_DAO.findBySectionIdAndUserId(sectionId, userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Request> findBySectionId(Long sectionId) throws LogicException {
        try {
            return REQUEST_DAO.findBySectionId(sectionId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
