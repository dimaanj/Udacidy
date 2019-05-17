package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.RequestBuilder;

import java.sql.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class RequestDao implements AbstractDao<Long, Request> {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private EntityBuilder<Request> builder = new RequestBuilder();

    private static final String INSERT = "INSERT INTO udacidy.request\n" +
            "    (user_id, creation_date_time)\n" +
            "VALUES (?, ?);";

    private static final String FIND_BY_SECTION_ID = "SELECT r.id, r.user_id, r.creation_date_time\n" +
            "    FROM request r\n" +
            "        JOIN request_data rd\n" +
            "            on r.id = rd.request_id\n" +
            "        JOIN section s\n" +
            "            on rd.section_id = s.id\n" +
            "    WHERE s.id = ?";

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
            statement.setLong(1, entity.getUserId());
            Date dateTime = Date
                    .from(entity.getCreationDateTime().atZone(ZoneId.systemDefault())
                            .toInstant());
            Object param = new java.sql.Timestamp(dateTime.getTime());
            statement.setObject(2, param);

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
            connection.commit();
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

    public Request findBySectionId(Long sectionId) throws DAOException {
        Connection connection = null;
        Request request = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_SECTION_ID);
            statement.setLong(1, sectionId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                request = builder.build(resultSet);
            }
            resultSet.close();
            return request;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
