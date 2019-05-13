package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.SectionDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public class SectionServiceImpl implements SectionService {
    private static final SectionDao SECTION_DAO = new SectionDao();

    @Override
    public Long create(Section section) throws LogicException {
        try {
            return SECTION_DAO.create(section);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long sectionId) throws LogicException {
        try {
            SECTION_DAO.deleteById(sectionId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Section> findByConferenceId(Long conferenceId) throws LogicException {
        try {
            return SECTION_DAO.findByConferenceId(conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
