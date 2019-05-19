package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.RequestDataDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestDataService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public class RequestDataServiceImpl implements RequestDataService {
    private static final RequestDataDao REQUEST_FORM_DAO = new RequestDataDao();

    @Override
    public Long create(RequestData requestForm) throws LogicException {
        try {
            return REQUEST_FORM_DAO.create(requestForm);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
