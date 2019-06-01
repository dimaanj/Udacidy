package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestStatus;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.RequestBuilder;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestDao implements AbstractDao<Long, Request> {
    private EntityBuilder<Request> builder = new RequestBuilder();

    private static final String INSERT = "INSERT INTO udacidy.request\n" +
            "    (user_id, creation_date_time, status, conference_id)\n" +
            "VALUES (?, ?, ?, ?);";

    private static final String FIND_BY_REQUEST_BY_USER_ID_AND_CONFERENCE_ID = "SELECT id, user_id, creation_date_time, status, conference_id\n" +
            "FROM request\n" +
            "WHERE user_id = ? and conference_id = ?";

    private static final String FIND_BY_ID = "SELECT id, user_id, creation_date_time, status, conference_id\n" +
            "FROM request\n" +
            "WHERE id = ?";

    private static final String FIND_ALL = "SELECT id, user_id, creation_date_time, status, conference_id\n" +
            "    FROM request";

    private static final String DELETE = "DELETE\n" +
            "FROM request\n" +
            "WHERE id = ?\n" +
            "\n";

    private static final String UPDATE_REQUEST_STATUS_BY_USER_ID_AND_CONFERENCE_ID = "UPDATE request\n" +
            "    SET status = ?\n" +
            "WHERE user_id = ? AND conference_id = ?";

    private static final String FIND_BY_USER_ID = "SELECT id, user_id, creation_date_time, status, conference_id\n" +
            "   FROM request\n" +
            "    WHERE user_id = ?\n";

    private static final String FIND_BY_CONFERENCE_ID = "SELECT id, user_id, creation_date_time, status, conference_id\n" +
            "   FROM request\n" +
            "    WHERE conference_id = ?\n";




    @Override
    public List<Request> findAll() throws DAOException {
        List<Request> requests = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = statement.executeQuery();
            requests = new ArrayList<>();
            while (resultSet.next()) {
                Request request = builder.build(resultSet);
                requests.add(request);
            }
            resultSet.close();
            statement.close();
            return requests;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Request findById(Long id) throws DAOException {
        Request request = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                request = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return request;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
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
    public Long create(Request entity) throws DAOException {
        Long requestId = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getUserId());
            Date dateTime = Date
                    .from(entity.getCreationDateTime().atZone(ZoneId.systemDefault())
                            .toInstant());
            Object param = new java.sql.Timestamp(dateTime.getTime());
            statement.setObject(2, param);
            statement.setString(3, entity.getRequestStatus().getStatus().toUpperCase());
            statement.setLong(4, entity.getConferenceId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    requestId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }
            statement.close();
            return requestId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Request entity) throws DAOException {
        throw new UnsupportedOperationException("RequestDao doesn't support 'update()'");
    }

    public Request findByUserIdAndConferenceId(Long userId, Long conferenceId) throws DAOException {
        Request request = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_REQUEST_BY_USER_ID_AND_CONFERENCE_ID);
            statement.setLong(1, userId);
            statement.setLong(2, conferenceId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                request = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return request;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void updateRequestStatusByUserIdAndConferenceId(Long userId, Long conferenceId, RequestStatus requestStatus) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_REQUEST_STATUS_BY_USER_ID_AND_CONFERENCE_ID);
            statement.setString(1, requestStatus.toString());
            statement.setLong(2, userId);
            statement.setLong(3, conferenceId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Request> findByUserId(Long userId) throws DAOException {
        List<Request> requests = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            requests = new ArrayList<>();
            while (resultSet.next()) {
                Request request = builder.build(resultSet);
                requests.add(request);
            }
            resultSet.close();
            statement.close();
            return requests;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Request> findByConferenceId(Long conferenceId) throws DAOException {
        List<Request> requests = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_CONFERENCE_ID);
            statement.setLong(1, conferenceId);
            ResultSet resultSet = statement.executeQuery();
            requests = new ArrayList<>();
            while (resultSet.next()) {
                Request request = builder.build(resultSet);
                requests.add(request);
            }
            resultSet.close();
            statement.close();
            return requests;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
