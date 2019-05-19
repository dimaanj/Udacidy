package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.SectionBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDao implements AbstractDao<Long, Section> {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private EntityBuilder<Section> builder = new SectionBuilder();

    private static final String INSERT = "INSERT INTO udacidy.section\n" +
            "    (content_id, conference_id)\n" +
            "VALUES (?, ?);";

    private static final String FIND_BY_ID = "SELECT s.id, s.content_id, s.conference_id\n" +
            "FROM section s\n" +
            "WHERE s.id = ?";

    private static final String DELETE = "DELETE\n" +
            "FROM udacidy.section\n" +
            "WHERE id = ?";

    private static final String SELECT_BY_CONFERENCE_ID = "SELECT s.id, s.content_id, s.conference_id\n" +
            "FROM udacidy.section s\n" +
            "    JOIN conference c\n" +
            "        on s.conference_id = c.id\n" +
            "WHERE c.id = ?";

    private static final String DELETE_CONTENT = "DELETE FROM udacidy.content\n" +
            "WHERE id = ?;";

    @Override
    public List<Section> findAll() throws DAOException {
        return null;
    }

    @Override
    public Section findById(Long id) throws DAOException {
        Section section = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                section = builder.build(resultSet);
            }
            resultSet.close();
            return section;
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
    public Long create(Section entity) throws DAOException {
        Long sectionId = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getContentId());
            statement.setLong(2, entity.getConferenceId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sectionId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }
            return sectionId;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(Section entity) throws DAOException {

    }


    public List<Section> findByConferenceId(Long conferenceId) throws DAOException {
        List<Section> sections = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CONFERENCE_ID);
            statement.setLong(1, conferenceId);
            ResultSet resultSet = statement.executeQuery();
            sections = new ArrayList<>();
            while (resultSet.next()) {
                Section section = builder.build(resultSet);
                sections.add(section);
            }
            resultSet.close();
            return sections;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    public void deleteSectionWithTheirContent(Long sectionId, Long contentId) throws DAOException {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, sectionId);
            statement.execute();

            statement = connection.prepareStatement(DELETE_CONTENT);
            statement.setLong(1, contentId);
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }


}
