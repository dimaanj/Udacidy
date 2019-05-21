package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public interface RequestDataService {
    Long create(RequestData requestForm) throws LogicException;
}
