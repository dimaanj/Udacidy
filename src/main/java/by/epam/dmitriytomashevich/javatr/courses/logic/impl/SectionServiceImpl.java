package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.SectionDao;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public class SectionServiceImpl implements SectionService {
    private final SectionDao sectionDao = new SectionDao();

    @Override
    public Long create(Section section) throws LogicException {
        try {
            return sectionDao.create(section);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void delete(Long sectionId) throws LogicException {
        try {
            sectionDao.deleteById(sectionId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<Section> findSectionsByConferenceId(Long conferenceId) throws LogicException {
        try {
            return sectionDao.findByConferenceId(conferenceId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public Section findById(Long sectionId) throws LogicException {
        try {
            return sectionDao.findById(sectionId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void deleteSectionWithTheirContent(Long sectionId, Long contentId) throws LogicException {
        try {
            sectionDao.deleteSectionWithTheirContent(sectionId, contentId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
