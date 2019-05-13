package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.SectionBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectionDao implements AbstractDao<Long, Section> {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private EntityBuilder<Section> builder = new SectionBuilder();

    private static final String INSERT = "INSERT INTO udacidy.section\n" +
            "    (content_id, conference_id)\n" +
            "VALUES (?, ?);";

    private static final String DELETE = "DELETE\n" +
            "FROM udacidy.section\n" +
            "WHERE id = ?";

    private static final String SELECT_BY_CONFERENCE_ID = "SELECT s.id, s.content_id, s.conference_id\n" +
            "FROM udacidy.section s\n" +
            "    JOIN conference c\n" +
            "        on s.conference_id = c.id\n" +
            "WHERE c.id = ?";

    @Override
    public List<Section> findAll() throws DAOException {
        return null;
    }

    @Override
    public Section findById(Long id) throws DAOException {
        return null;
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
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setLong(1, entity.getContentId());
            statement.setLong(2, entity.getConferenceId());
            statement.execute();
            return entity.getId();
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


}
