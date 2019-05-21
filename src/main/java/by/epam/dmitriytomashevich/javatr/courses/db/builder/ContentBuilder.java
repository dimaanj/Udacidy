package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContentBuilder implements EntityBuilder<Content> {
    @Override
    public Content build(ResultSet resultSet) throws DAOException {
        Content content = new Content();
        try {
            content.setId(resultSet.getLong("id"));
            content.setContent(resultSet.getString("content"));
            return content;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
