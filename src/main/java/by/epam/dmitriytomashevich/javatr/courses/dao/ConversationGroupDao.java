package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConversationGroupService;

import java.sql.*;
import java.util.List;

public class ConversationGroupDao implements AbstractDao<Long, ConversationGroup> {
    private ConnectionPool pool = ConnectionPool.getInstance();

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
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_CONVERSATION_GROUP);
            statement.setLong(1, entity.getConversationId());
            statement.setLong(2, entity.getUserId());
            statement.execute();
            return entity.getId();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(ConversationGroup entity) {

    }


    public ConversationGroup findByConversationId(Long id) throws DAOException {
        Connection connection = null;
        ConversationGroup group = null;
        try {
            connection = pool.getConnection();
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
            return group;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    public ConversationGroup findByUserIdAndByConversationId(Long userId, Long conversationId) throws DAOException {
        Connection connection = null;
        ConversationGroup group = null;
        try {
            connection = pool.getConnection();
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
            return group;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }


}
