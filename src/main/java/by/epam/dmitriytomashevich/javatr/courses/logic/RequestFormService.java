package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public interface RequestFormService {
    Long create(RequestData requestForm) throws LogicException;
    void deleteByRequestId(Long requestId) throws LogicException;
}
