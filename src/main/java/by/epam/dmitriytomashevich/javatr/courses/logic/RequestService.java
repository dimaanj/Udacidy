package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestStatus;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface RequestService {
    Long create(Request request) throws LogicException;
    void delete(Long requestId) throws LogicException;
    Request findByUserIdAndConferenceId(Long userId, Long conferenceId) throws LogicException;
    List<Request> findByConferenceId(Long conferenceId) throws LogicException;
    List<Request> findAll() throws LogicException;
    void updateRequestStatusByUserIdAndConferenceId(Long userId, Long conferenceId, RequestStatus requestStatus) throws LogicException;
    Request findById(Long id) throws LogicException;
    List<Request> findByUserId(Long userId) throws LogicException;

}
