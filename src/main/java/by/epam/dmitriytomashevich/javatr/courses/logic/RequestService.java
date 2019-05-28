package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface RequestService {
    Long create(Request request) throws LogicException;
    List<Request> findBySectionId(Long sectionId) throws LogicException;
    void delete(Long requestId) throws LogicException;
    List<Request> findAllByUserIdAndConferenceId(Long userId, Long conferenceId) throws LogicException;
    Request findById(Long requestId) throws LogicException;
    void deleteFullRequestByConferenceIdAndUserId(Long conferenceId, Long userId) throws LogicException;
    List<Request> findAll() throws LogicException;
}
