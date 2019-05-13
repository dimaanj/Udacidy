package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public interface SectionService {
    Long create(Section section) throws LogicException;
    void delete(Long sectionId) throws LogicException;
    List<Section> findByConferenceId(Long conferenceId) throws LogicException;
}
