package by.epam.dmitriytomashevich.javatr.courses.db.dao;

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
    private final Connection connection;

    private static final String INSERT = "INSERT INTO udacidy.request\n" +
            "    (user_id, creation_date_time, status, conference_id, section_id)\n" +
            "VALUES (?, ?, ?, ?, ?);";

//    private static final String FIND_BY_SECTION_ID_AND_USER_ID = "SELECT id, user_id, creation_date_time, status, conference_id, section_id\n" +
//            "    FROM request \n" +
//            "    WHERE section_id = ? and user_id = ?";

    private static final String FIND_BY_SECTION_ID = "SELECT id, user_id, creation_date_time, status, conference_id, section_id\n" +
            "FROM request\n" +
            "WHERE section_id = ?";

//    private static final String DELETE = "DELETE FROM udacidy.request\n" +
//            "    WHERE id = ?";

    private static final String DELETE_BY_CONFERENCE_ID_AND_USER_ID = "DELETE FROM udacidy.request\n" +
            "    WHERE conference_id = ? AND user_id = ?";

    private static final String FIND_ALL_BY_REQUEST_BY_USER_ID_AND_CONFERENCE_ID = "SELECT id, user_id, creation_date_time, status, conference_id, section_id\n" +
            "FROM request\n" +
            "WHERE user_id = ? and conference_id = ?";

    private static final String FIND_ALL_BY_USER_ID = "SELECT id, user_id, creation_date_time, status, conference_id, section_id\n" +
            "FROM request\n" +
            "WHERE user_id = ?";

    private static final String FIND_BY_ID = "SELECT id, user_id, creation_date_time, status, conference_id, section_id\n" +
            "FROM request\n" +
            "WHERE id = ?";

    public RequestDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Request> findAll() throws DAOException {
        return null;
    }

    @Override
    public Request findById(Long id) throws DAOException {
        Request request = null;
        try {
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
//        try {
//            PreparedStatement statement = connection.prepareStatement(DELETE);
//            statement.setLong(1, id);
//            statement.execute();
//            statement.close();
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
    }

    @Override
    public Long create(Request entity) throws DAOException {
        Long requestFormId = null;
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getUserId());
            Date dateTime = Date
                    .from(entity.getCreationDateTime().atZone(ZoneId.systemDefault())
                            .toInstant());
            Object param = new java.sql.Timestamp(dateTime.getTime());
            statement.setObject(2, param);
            statement.setString(3, entity.getRequestStatus().getStatus().toUpperCase());
            statement.setLong(4, entity.getConferenceId());
            statement.setLong(5, entity.getSectionId());

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
    public void update(Request entity) throws DAOException {

    }

    public Request findBySectionIdAndUserId(Long sectionId, Long userId) throws DAOException {
        Request request = null;
//        try {
//            PreparedStatement statement = connection.prepareStatement(FIND_BY_SECTION_ID_AND_USER_ID);
//            statement.setLong(1, sectionId);
//            statement.setLong(2, userId);
//
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                request = builder.build(resultSet);
//            }
//            resultSet.close();
//            statement.close();
            return request;
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
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
            statement.close();
            return requests;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Request> findAllRequestByUserIdAndConferenceId(Long userId, Long conferenceId) throws DAOException {
        List<Request> requests = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_REQUEST_BY_USER_ID_AND_CONFERENCE_ID);
            statement.setLong(1, userId);
            statement.setLong(2, conferenceId);
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

    public List<Request> findAllByUserId(Long userId) throws DAOException {
        List<Request> requests = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
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

    public void deleteFullRequestByConferenceId(Long conferenceId, Long userId) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_CONFERENCE_ID_AND_USER_ID);
            statement.setLong(1, conferenceId);
            statement.setLong(2, userId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
