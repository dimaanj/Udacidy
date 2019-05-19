package by.epam.dmitriytomashevich.javatr.courses.logic.builder;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestBuilder implements EntityBuilder<Request> {
    @Override
    public Request build(ResultSet resultSet) throws DAOException {
        Request request = new Request();
        try {
            request.setId(resultSet.getLong("id"));
            request.setUserId(resultSet.getLong("user_id"));
            request.setCreationDateTime(resultSet.getTimestamp("creation_date_time").toLocalDateTime());
            return request;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}