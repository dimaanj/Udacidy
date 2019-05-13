package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public interface ContentService {
    Long create(Content content) throws LogicException;
    Content findById(Long id) throws LogicException;
    Content findByConferenceId(Long conferenceId) throws LogicException;
    void update(Content content) throws LogicException;
    void delete(Long id) throws LogicException;
    Content findBySectionId(Long sectionId) throws LogicException;
}
