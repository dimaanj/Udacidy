package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestStatus;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
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
            request.setRequestStatus(RequestStatus.valueOf(resultSet.getString("status")));
            return request;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
