package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public interface RequestDataService {
    Long create(RequestData requestForm) throws LogicException;
}
