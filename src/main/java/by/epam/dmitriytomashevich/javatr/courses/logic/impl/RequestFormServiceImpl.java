package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.RequestFormDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestFormService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public class RequestFormServiceImpl implements RequestFormService {
    private static final RequestFormDao REQUEST_FORM_DAO = new RequestFormDao();

    @Override
    public Long create(RequestForm requestForm) throws LogicException {
        try {
            return REQUEST_FORM_DAO.create(requestForm);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
