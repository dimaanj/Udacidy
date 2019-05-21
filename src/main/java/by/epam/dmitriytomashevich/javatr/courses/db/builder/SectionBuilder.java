package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionBuilder implements EntityBuilder<Section> {
    @Override
    public Section build(ResultSet resultSet) throws DAOException {
        Section section = new Section();
        try {
            section.setId(resultSet.getLong("id"));
            section.setContentId(resultSet.getLong("content_id"));
            section.setConferenceId(resultSet.getLong("conference_id"));
            return section;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
