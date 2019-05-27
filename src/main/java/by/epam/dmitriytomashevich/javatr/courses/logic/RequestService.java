package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface RequestService {
    Long create(Request request) throws LogicException;
    Request findBySectionIdAndUserId(Long sectionId, Long userId) throws LogicException;
    List<Request> findBySectionId(Long sectionId) throws LogicException;
    void deleteRequestWithRequestData(Long requestId) throws LogicException;
    Request findByUserIdAndConferenceId(Long userId, Long conferenceId) throws LogicException;

}
