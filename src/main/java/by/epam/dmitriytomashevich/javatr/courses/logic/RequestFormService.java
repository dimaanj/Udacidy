package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public interface RequestFormService {
    Long create(RequestForm requestForm) throws LogicException;
}
