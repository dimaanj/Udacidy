package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.ConferenceBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDao implements AbstractDao<Long, Conference> {
    private EntityBuilder<Conference> builder = new ConferenceBuilder();
    private final Connection connection;

    private static final String INSERT_CONFERENCE = "INSERT INTO udacidy.conference\n" +
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

    private static final String DELETE_CONTENT = "DELETE FROM udacidy.content\n" +
            "WHERE id = ?;";

    private static final String FIND_ALL_CONFERENCES_AS_USER_REQUESTS = "SELECT DISTINCT c.id, c.content_id, c.author_id\n" +
            "    FROM conference c\n" +
            "        JOIN section s on c.id = s.conference_id\n" +
            "        JOIN request r on s.id = r.section_id\n" +
            "WHERE r.user_id = ?";



    public ConferenceDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Conference> findAll() throws DAOException {
        return null;
    }

    @Override
    public Conference findById(Long id) throws DAOException {
        Conference conference = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                conference = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return conference;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Long create(Conference entity) throws DAOException {

        Long conferenceId = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement(INSERT_CONFERENCE, Statement.RETURN_GENERATED_KEYS);
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
            statement.close();
            return conferenceId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Conference entity) {

    }

    public List<Conference> findSomeLastConferences(int count) throws DAOException {
        List<Conference> conferences = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_SOME_LAST_CONFERENCES);
            statement.setLong(1, count);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Conference conference = builder.build(resultSet);
                conferences.add(conference);
            }
            resultSet.close();
            statement.close();
            return conferences;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    public Conference findTheOldest() throws DAOException {
        Conference conference = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_THE_OLDEST);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                conference = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return conference;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    public List<Conference> findSomeOlderStartsWithConferenceId(Long id) throws DAOException {
        List<Conference> conferences = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement( FIND_SOME_OLDER_STARTS_WITH_CONFERENCE_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Conference conference = builder.build(resultSet);
                conferences.add(conference);
            }
            resultSet.close();
            statement.close();
            return conferences;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    public void deleteConferenceWithTheirContent(Long conferenceId, Long contentId) throws DAOException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, conferenceId);
            statement.execute();

            statement = connection.prepareStatement(DELETE_CONTENT);
            statement.setLong(1, contentId);
            statement.execute();

            connection.commit();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Conference> findAllConferencesAsUserRequestsByUserId(Long userId) throws DAOException {
        List<Conference> conferences = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_CONFERENCES_AS_USER_REQUESTS);
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Conference conference = builder.build(resultSet);
                conferences.add(conference);
            }
            resultSet.close();
            statement.close();
            return conferences;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }
}
