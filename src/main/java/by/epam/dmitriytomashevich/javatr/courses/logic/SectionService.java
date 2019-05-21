package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface SectionService {
    Long create(Section section) throws LogicException;
    void delete(Long sectionId) throws LogicException;
    List<Section> findSectionsByConferenceId(Long conferenceId) throws LogicException;
    Section findById(Long sectionId) throws LogicException;

    void deleteSectionWithTheirContent(Long sectionId, Long contentId) throws LogicException;
}
