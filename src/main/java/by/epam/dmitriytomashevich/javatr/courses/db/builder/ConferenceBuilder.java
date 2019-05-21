package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceBuilder implements EntityBuilder<Conference> {
    @Override
    public Conference build(ResultSet resultSet) throws DAOException {
        Conference conference = new Conference();
        try {
            conference.setId(resultSet.getLong("id"));
            conference.setAuthorId(resultSet.getLong("author_id"));
            conference.setContentId(resultSet.getLong("content_id"));
            return conference;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
