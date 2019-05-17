package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestForm;

import java.sql.*;
import java.util.List;

public class RequestFormDao implements AbstractDao<Long, RequestForm> {
    private ConnectionPool pool = ConnectionPool.getInstance();

    private static final String INSERT = "INSERT INTO udacidy.request_form\n" +
            "    (section_id, request_id)\n" +
            "VALUES (?, ?);";


    @Override
    public List<RequestForm> findAll() throws DAOException {
        return null;
    }

    @Override
    public RequestForm findById(Long id) throws DAOException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws DAOException {

    }

    @Override
    public Long create(RequestForm entity) throws DAOException {
        Long requestFormId = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
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
            return requestFormId;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(RequestForm entity) throws DAOException {

    }
}
