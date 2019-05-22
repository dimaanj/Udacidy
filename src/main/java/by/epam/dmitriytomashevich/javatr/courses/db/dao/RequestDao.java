package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
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
    private final Connection connection;

    private static final String INSERT = "INSERT INTO udacidy.request\n" +
            "    (user_id, creation_date_time, status)\n" +
            "VALUES (?, ?, ?);";

    private static final String FIND_BY_SECTION_ID_AND_USER_ID = "SELECT r.id, r.user_id, r.creation_date_time, r.status\n" +
            "    FROM section s\n" +
            "        JOIN request_data rd on s.id = rd.section_id\n" +
            "        JOIN request r on rd.request_id = r.id\n" +
            "        JOIN user u on r.user_id = u.id\n" +
            "    WHERE s.id = ? and u.id = ?";

    private static final String FIND_BY_SECTION_ID = "SELECT r.id, r.user_id, r.creation_date_time, r.status\n" +
            "FROM section s\n" +
            "         JOIN request_data rd on s.id = rd.section_id\n" +
            "         JOIN request r on rd.request_id = r.id\n" +
            "WHERE s.id = ?";

    private static final String DELETE = "DELETE FROM udacidy.request\n" +
            "    WHERE id = ?";

    private static final String DELETE_REQUEST_FORM_BY_REQUEST_ID = "DELETE FROM request_data \n" +
            "    WHERE request_id = ?";

    private static final String FIND_REQUEST_BY_USER_ID_AND_CONFERENCE_ID = "SELECT r.id, r.user_id, r.creation_date_time, r.status\n" +
            "FROM user u\n" +
            "    JOIN request r on u.id = r.user_id\n" +
            "    JOIN request_data rd on r.id = rd.request_id\n" +
            "    JOIN section s on rd.section_id = s.id\n" +
            "    JOIN conference c on s.conference_id = c.id\n" +
            "    WHERE u.id = ? and conference_id = ?";

    public RequestDao(Connection connection){
        this.connection = connection;
    }

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
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getUserId());
            Date dateTime = Date
                    .from(entity.getCreationDateTime().atZone(ZoneId.systemDefault())
                            .toInstant());
            Object param = new java.sql.Timestamp(dateTime.getTime());
            statement.setObject(2, param);
            statement.setString(3, entity.getRequestStatus().getStatus().toUpperCase());

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
        }
    }

    @Override
    public void update(Request entity) throws DAOException {

    }

    public Request findBySectionIdAndUserId(Long sectionId, Long userId) throws DAOException {
        Request request = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_SECTION_ID_AND_USER_ID);
            statement.setLong(1, sectionId);
            statement.setLong(2, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                request = builder.build(resultSet);
            }
            resultSet.close();
            return request;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Request> findBySectionId(Long sectionId) throws DAOException {
        List<Request> requests = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_SECTION_ID);
            statement.setLong(1, sectionId);

            ResultSet resultSet = statement.executeQuery();
            requests = new ArrayList<>();
            while (resultSet.next()) {
                Request request = builder.build(resultSet);
                requests.add(request);
            }
            resultSet.close();
            return requests;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void deleteRequestWithRequestData(Long requestId) throws DAOException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST_FORM_BY_REQUEST_ID);
            statement.setLong(1, requestId);
            statement.execute();

            statement = connection.prepareStatement(DELETE);
            statement.setLong(1, requestId);
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Request findRequestByUserIdAndConferenceId(Long userId, Long conferenceId) throws DAOException {
        Request request = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_REQUEST_BY_USER_ID_AND_CONFERENCE_ID);
            statement.setLong(1, userId);
            statement.setLong(2, conferenceId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                request = builder.build(resultSet);
            }
            resultSet.close();
            return request;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
