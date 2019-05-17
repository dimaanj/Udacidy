package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;

import java.sql.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RequestDao implements AbstractDao<Long, Request> {
    private ConnectionPool pool = ConnectionPool.getInstance();

    private static final String INSERT = "INSERT INTO udacidy.request\n" +
            "    (comment, user_id, creation_date_time)\n" +
            "VALUES (?, ?, ?);";

    @Override
    public List<Request> findAll() throws DAOException {
        return null;
    }

    @Override
    public Request findById(Long id) throws DAOException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws DAOException {

    }

    @Override
    public Long create(Request entity) throws DAOException {
        Long requestFormId = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getComment());
            statement.setLong(2, entity.getUserId());
            Date dateTime = Date
                    .from(entity.getCreationDateTime().atZone(ZoneId.systemDefault())
                            .toInstant());
            Object param = new java.sql.Timestamp(dateTime.getTime());
            statement.setObject(3, param);

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
    public void update(Request entity) throws DAOException {

    }
}
