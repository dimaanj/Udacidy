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

    private static final String DELETE_CONTENT = "DELETE FROM udacidy.content\n" +
            "WHERE id = ?;";

    private static final String COUNT_CONFERENCES = "SELECT COUNT(*) number_of_conferences\n" +
            "            FROM conference";

    private static final String SELECT_FROM_ROW_NUMBER_TO_LIMIT = "WITH NumberedMyTable AS\n" +
            "         (\n" +
            "             SELECT\n" +
            "                 id,\n" +
            "                 content_id,\n" +
            "                 author_id,\n" +
            "                 ROW_NUMBER() OVER (ORDER BY id DESC) AS RowNumber\n" +
            "             FROM\n" +
            "                 conference\n" +
            "         )\n" +
            "SELECT\n" +
            "    id,\n" +
            "    content_id,\n" +
            "    author_id\n" +
            "FROM\n" +
            "    NumberedMyTable\n" +
            "WHERE\n" +
            "    RowNumber > ?\n" +
            "ORDER BY id DESC\n" +
            "LIMIT ?";

    @Override
    public List<Conference> findAll() throws DAOException {
        throw new UnsupportedOperationException("Conference DAO doesn't support 'findAll()'");
    }

    @Override
    public Conference findById(Long id) throws DAOException {
        Conference conference = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
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
    public Long create(Conference entity) throws DAOException {
        Long conferenceId = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
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
        throw new UnsupportedOperationException("Conference DAO doesn't support 'update'");
    }

    public List<Conference> findSomeLastConferences(int count) throws DAOException {
        List<Conference> conferences = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
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
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
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

    public void deleteConferenceWithTheirContent(Long conferenceId, Long contentId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, conferenceId);
            statement.execute();

            statement = connection.prepareStatement(DELETE_CONTENT);
            statement.setLong(1, contentId);
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Long countNumber() throws DAOException {
        Long number = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(COUNT_CONFERENCES);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                number = resultSet.getLong("number_of_conferences");
            }
            resultSet.close();
            statement.close();
            return number;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Conference> findFromRowIndexToLimit(Long rowIndex, Long updateAmount) throws DAOException {
        List<Conference> conferences = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_FROM_ROW_NUMBER_TO_LIMIT);
            statement.setLong(1, rowIndex);
            statement.setLong(2, updateAmount);

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
