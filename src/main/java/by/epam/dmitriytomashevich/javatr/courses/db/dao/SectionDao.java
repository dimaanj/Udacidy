package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.SectionBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDao implements AbstractDao<Long, Section> {
    private EntityBuilder<Section> builder = new SectionBuilder();
    private final Connection connection;

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

    public SectionDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Section> findAll() throws DAOException {
        return null;
    }

    @Override
    public Section findById(Long id) throws DAOException {
        Section section = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                section = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return section;
        } catch (SQLException e) {
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
    public Long create(Section entity) throws DAOException {
        Long sectionId = null;
        try {
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
            statement.close();
            return sectionId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Section entity) throws DAOException {

    }


    public List<Section> findByConferenceId(Long conferenceId) throws DAOException {
        List<Section> sections = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CONFERENCE_ID);
            statement.setLong(1, conferenceId);
            ResultSet resultSet = statement.executeQuery();
            sections = new ArrayList<>();
            while (resultSet.next()) {
                Section section = builder.build(resultSet);
                sections.add(section);
            }
            resultSet.close();
            statement.close();
            return sections;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void deleteSectionWithTheirContent(Long sectionId, Long contentId) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, sectionId);
            statement.execute();

            statement = connection.prepareStatement(DELETE_CONTENT);
            statement.setLong(1, contentId);
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


}
