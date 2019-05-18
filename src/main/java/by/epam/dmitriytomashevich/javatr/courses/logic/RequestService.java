package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public interface RequestService {
    Long create(Request request) throws LogicException;
    void delete(Long id) throws LogicException;
    Request findBySectionIdAndUserId(Long sectionId, Long userId) throws LogicException;
    List<Request> findBySectionId(Long sectionId) throws LogicException;
}
