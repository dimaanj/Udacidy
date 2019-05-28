package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;

import java.sql.*;
import java.util.List;

public class ConversationGroupDao implements AbstractDao<Long, ConversationGroup> {
    private final Connection connection;

    private static final String INSERT_CONVERSATION_GROUP = "INSERT INTO udacidy.conversation_group (conversation_id, user_id) " +
            "VALUES (?, ?)";

    private static final String SELECT_BY_CONVERSATION_ID = "SELECT c.id, c.conversation_id, c.user_id\n" +
            "FROM conversation_group c\n" +
            "WHERE c.conversation_id = ?";

    private static final String SELECT_BY_USER_ID_AND_CONVERSATION_ID = "SELECT c.id, c.conversation_id, c.user_id\n" +
            "FROM conversation_group c\n" +
            "         JOIN user u on c.user_id = u.id\n" +
            "         JOIN user_role ur on u.id = ur.id\n" +
            "WHERE c.user_id = ? and c.conversation_id = ?";

    private static final String DELETE_BY_CONVERSATION_ID = "DELETE FROM conversation_group\n" +
            "    WHERE conversation_id = ?";

    public ConversationGroupDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<ConversationGroup> findAll() {
        return null;
    }

    @Override
    public ConversationGroup findById(Long id) throws DAOException {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Long create(ConversationGroup entity) throws DAOException {
        Long conversationGroupId = null;
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_CONVERSATION_GROUP, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getConversationId());
            statement.setLong(2, entity.getUserId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conversationGroupId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }
            statement.close();
            return conversationGroupId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(ConversationGroup entity) {

    }


    public ConversationGroup findByConversationId(Long id) throws DAOException {
        ConversationGroup group = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CONVERSATION_ID);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = new ConversationGroup();
                group.setId(resultSet.getLong("id"));
                group.setConversationId(resultSet.getLong("conversation_id"));
                group.setUserId(resultSet.getLong("user_id"));
            }
            resultSet.close();
            statement.close();
            return group;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public ConversationGroup findByUserIdAndByConversationId(Long userId, Long conversationId) throws DAOException {
        ConversationGroup group = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_AND_CONVERSATION_ID);
            statement.setLong(1, userId);
            statement.setLong(2, conversationId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = new ConversationGroup();
                group.setId(resultSet.getLong("id"));
                group.setConversationId(resultSet.getLong("conversation_id"));
                group.setUserId(resultSet.getLong("user_id"));
            }
            resultSet.close();
            statement.close();
            return group;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void deleteByConversationId(Long conversationId) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_CONVERSATION_ID);
            statement.setLong(1, conversationId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


}
