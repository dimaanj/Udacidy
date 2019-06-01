package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.RequestFormDao;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestFormService;

import java.util.List;

public class RequestFormServiceImpl implements RequestFormService {
    private final RequestFormDao requestFormDao = new RequestFormDao();

    @Override
    public Long create(RequestForm requestForm) throws LogicException {
        try {
            return requestFormDao.create(requestForm);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<RequestForm> findByRequestId(Long requestId) throws LogicException {
        try {
            return requestFormDao.findByRequestId(requestId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long id) throws LogicException {
        try {
            requestFormDao.deleteById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
