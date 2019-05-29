package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface ConferenceService {
    Long create(Conference conference) throws LogicException;
    Conference findById(Long id);
    List<Conference> findSomeLastConferences(int amount) throws LogicException;
    Conference getById(Long id) throws LogicException;
    void delete(Long id) throws LogicException;
    Conference getTheOldest() throws LogicException;
    void deleteConferenceWithTheirContent(Long conferenceId, Long contentId) throws LogicException;
    Long countNumberOfConferences() throws LogicException;
    List<Conference> findFromRowIndexToLimit(Long rowIndex, Long limit) throws LogicException;
}
