package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.RequestDataDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestDataService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public class RequestDataServiceImpl implements RequestDataService {
    private final RequestDataDao requestDataDao;

    public RequestDataServiceImpl(DaoFactory daoFactory){
        requestDataDao = daoFactory.createRequestDataDao();
    }

    @Override
    public Long create(RequestData requestForm) throws LogicException {
        try {
            return requestDataDao.create(requestForm);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
