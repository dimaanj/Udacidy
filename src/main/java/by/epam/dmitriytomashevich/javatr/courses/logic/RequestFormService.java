package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface RequestFormService {
    Long create(RequestForm requestForm) throws LogicException;
    List<RequestForm> findByRequestId(Long requestId) throws LogicException;
    void delete(Long id) throws LogicException;
}
