package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestDataBuilder implements EntityBuilder<RequestData> {
    @Override
    public RequestData build(ResultSet resultSet) throws DAOException {
        try {
            RequestData requestData = new RequestData();
            requestData.setId(resultSet.getLong("id"));
            requestData.setSectionId(resultSet.getLong("section_id"));
            requestData.setRequestId(resultSet.getLong("request_id"));
            return requestData;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
