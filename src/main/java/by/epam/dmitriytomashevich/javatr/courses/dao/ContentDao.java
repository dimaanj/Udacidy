package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.ContentBuilder;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.EntityBuilder;

import java.sql.*;
import java.util.List;

public class ContentDao implements AbstractDao<Long, Content> {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private EntityBuilder<Content> builder = new ContentBuilder();

    private static final String INSERT_CONTENT = "INSERT INTO udacidy.content (content)\n" +
            "VALUES(?)";

    private static final String SELECT_BY_ID = "SELECT id, content\n" +
            "FROM content\n" +
            "WHERE id=?";

    private static final String SELECT_BY_CONFERENCE_ID ="SELECT c.id, c.content\n" +
            "FROM udacidy.conference l\n" +
            "         JOIN content c\n" +
            "             ON l.content_id = c.id\n" +
            "    WHERE l.id = ?";

    private static final String UPDATE_CONTENT = "UPDATE udacidy.content c\n" +
            "SET c.content = ?\n" +
            "WHERE c.id = ?";

    private static final String DELETE = "DELETE FROM udacidy.content\n" +
            "WHERE id = ?;";

    private static final String SELECT_BY_SECTION_ID = "SELECT c.id, c.content\n" +
            "FROM udacidy.section s\n" +
            "    JOIN content c\n" +
            "        on s.content_id = c.id\n" +
            "WHERE s.id = ?";

    @Override
    public List<Content> findAll() throws DAOException {
        return null;
    }

    @Override
    public Content findById(Long id) throws DAOException {
        Connection connection = null;
        Content content = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
               content = builder.build(resultSet);
            }
            resultSet.close();
            return content;
        } catch (SQLException e) {
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
    public Long create(Content entity) throws DAOException {
        Connection connection = null;
        Long contentId = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(INSERT_CONTENT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getContent());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    contentId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }
            return contentId;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(Content entity) throws DAOException {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CONTENT);
            statement.setString(1, entity.getContent());
            statement.setLong(2, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    public Content findByConferenceId(Long conferenceId) throws DAOException {
        Connection connection = null;
        Content content = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CONFERENCE_ID);
            statement.setLong(1, conferenceId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                content = builder.build(resultSet);
            }
            resultSet.close();
            return content;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    public Content findBySectionId(Long sectionId) throws DAOException {
        Connection connection = null;
        Content content = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_SECTION_ID);
            statement.setLong(1, sectionId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                content = builder.build(resultSet);
            }
            resultSet.close();
            return content;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
