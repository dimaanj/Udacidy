package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public interface RequestService {
    Long create(Request request) throws LogicException;
    void delete(Long id) throws LogicException;
    Request findBySectionIdAndUserId(Long sectionId, Long userId) throws LogicException;
}
