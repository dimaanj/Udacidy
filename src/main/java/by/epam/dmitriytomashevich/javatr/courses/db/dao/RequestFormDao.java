package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.RequestFormBuilder;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestFormDao implements AbstractDao<Long, RequestForm> {
    private final EntityBuilder<RequestForm> builder = new RequestFormBuilder();

    private static final String INSERT = "INSERT INTO udacidy.request_form\n" +
            "    (section_id, request_id)\n" +
            "VALUES (?, ?);";

    private static final String FIND_BY_REQUEST_ID = "SELECT rf.id, rf.section_id, rf.request_id\n" +
            "    FROM request_form rf\n" +
            "        JOIN request r on rf.request_id = r.id\n" +
            "    WHERE r.id=?";

    private static final String DELETE = "DELETE request_form\n" +
            "    FROM request_form\n" +
            "    WHERE id = ?";


    @Override
    public List<RequestForm> findAll() {
        throw new UnsupportedOperationException("RequestFormDao doesn't support 'findAll()'");
    }

    @Override
    public RequestForm findById(Long id) {
        throw new UnsupportedOperationException("RequestFormDao doesn't support 'findById()'");
    }

    @Override
    public void deleteById(Long id) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Long create(RequestForm entity) throws DAOException {
        Long requestFormId;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getSectionId());
            statement.setLong(2, entity.getRequestId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    requestFormId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }
            statement.close();
            return requestFormId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(RequestForm entity) {
        throw new UnsupportedOperationException("RequestFormDao doesn't support 'update()'");
    }

    public List<RequestForm> findByRequestId(Long requestId) throws DAOException {
        List<RequestForm> requestForms;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_REQUEST_ID);
            statement.setLong(1, requestId);
            ResultSet resultSet = statement.executeQuery();
            requestForms = new ArrayList<>();
            while (resultSet.next()) {
                RequestForm requestForm = builder.build(resultSet);
                requestForms.add(requestForm);
            }
            resultSet.close();
            statement.close();
            return requestForms;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
