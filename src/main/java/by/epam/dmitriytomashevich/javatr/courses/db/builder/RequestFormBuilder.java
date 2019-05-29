package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestFormBuilder implements EntityBuilder<RequestForm> {
    @Override
    public RequestForm build(ResultSet resultSet) throws DAOException, SQLException {
        RequestForm requestForm = new RequestForm();
        requestForm.setId(resultSet.getLong("id"));
        requestForm.setSectionId(resultSet.getLong("section_id"));
        requestForm.setRequestId(resultSet.getLong("request_id"));
        return requestForm;
    }
}
