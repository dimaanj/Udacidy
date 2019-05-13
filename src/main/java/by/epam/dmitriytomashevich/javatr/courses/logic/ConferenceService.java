package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public interface ConferenceService {
    Long create(Conference conference) throws LogicException;
    List<Conference> findSomeLastConferences(int amount) throws LogicException;
    Conference getById(Long id) throws LogicException;
    void delete(Long id) throws LogicException;
    Conference getTheOldest() throws LogicException;
    List<Conference> findSomeOlderStartsWithConversationId(Long id) throws LogicException;
}
