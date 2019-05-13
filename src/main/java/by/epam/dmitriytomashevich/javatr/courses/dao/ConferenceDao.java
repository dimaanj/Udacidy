package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.ConferenceBuilder;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.EntityBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDao implements AbstractDao<Long, Conference> {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private EntityBuilder<Conference> builder = new ConferenceBuilder();

    private static final String INSERT_COURSE = "INSERT INTO udacidy.conference\n" +
            "    (content_id, author_id)\n" +
            "VALUES (?, ?);";

    private static final String SELECT_SOME_LAST_CONFERENCES = "SELECT l.id, l.content_id, l.author_id\n" +
            "        FROM udacidy.conference l\n" +
            "        JOIN content c2\n" +
            "        on l.content_id = c2.id\n" +
            "        ORDER BY l.id DESC \n" +
            "        LIMIT ?";

    private static final String SELECT_BY_ID = "SELECT l.id, l.content_id, l.author_id\n" +
            "FROM udacidy.conference l\n" +
            "    WHERE l.id = ?\n";

    private static final String DELETE = "DELETE FROM udacidy.conference\n" +
            "WHERE id = ?;";

    private static final String FIND_THE_OLDEST = "SELECT id, content_id, author_id\n" +
            "FROM udacidy.conference\n" +
            "ORDER BY id\n" +
            "LIMIT 1";

    private static final String FIND_SOME_OLDER_STARTS_WITH_CONFERENCE_ID = "SELECT id, content_id, author_id\n" +
            "FROM udacidy.conference\n" +
            "WHERE id < ?\n" +
            "ORDER BY id DESC\n" +
            "LIMIT 10";

    @Override
    public List<Conference> findAll() throws DAOException {
        return null;
    }

    @Override
    public Conference findById(Long id) throws DAOException {
        Conference conference = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                conference = builder.build(resultSet);
            }
            resultSet.close();
            return conference;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteById(Long id) throws DAOException {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Long create(Conference entity) throws DAOException {
        Connection connection = null;
        Long conferenceId = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(INSERT_COURSE, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getContentId());
            statement.setLong(2, entity.getAuthorId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conferenceId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }
            return conferenceId;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(Conference entity) {

    }

    public List<Conference> findSomeLastConferences(int count) throws DAOException {
        List<Conference> conferences = new ArrayList<>();
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_SOME_LAST_CONFERENCES);
            statement.setLong(1, count);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Conference conference = builder.build(resultSet);
                conferences.add(conference);
            }
            resultSet.close();
            return conferences;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    public Conference findTheOldest() throws DAOException {
        Conference conference = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_THE_OLDEST);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                conference = builder.build(resultSet);
            }
            resultSet.close();
            return conference;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    public List<Conference> findSomeOlderStartsWithConferenceId(Long id) throws DAOException {
        List<Conference> conferences = new ArrayList<>();
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement( FIND_SOME_OLDER_STARTS_WITH_CONFERENCE_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Conference conference = builder.build(resultSet);
                conferences.add(conference);
            }
            resultSet.close();
            return conferences;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
