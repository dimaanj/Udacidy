package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.RequestFormDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestFormService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public class RequestFormServiceImpl implements RequestFormService {
    private static final RequestFormDao REQUEST_FORM_DAO = new RequestFormDao();

    @Override
    public Long create(RequestData requestForm) throws LogicException {
        try {
            return REQUEST_FORM_DAO.create(requestForm);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void deleteByRequestId(Long requestId) throws LogicException {
        try {
            REQUEST_FORM_DAO.deleteByRequestId(requestId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
