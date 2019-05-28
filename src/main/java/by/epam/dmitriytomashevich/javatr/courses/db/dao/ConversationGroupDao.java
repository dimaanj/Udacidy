package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.ConversationGroupBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;

import java.sql.*;
import java.util.List;

public class ConversationGroupDao implements AbstractDao<Long, ConversationGroup> {
    private final Connection connection;
    private final EntityBuilder<ConversationGroup> builder = new ConversationGroupBuilder();

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

    private static final String FIND_BY_USER_ID_AND_CONVERSATION_TYPE = "SELECT cg.id, cg.conversation_id, cg.user_id\n" +
            "            FROM conversation_group cg\n" +
            "                JOIN conversation c on cg.conversation_id = c.id\n" +
            "                JOIN conversation_type t on c.id = t.id\n" +
            "            WHERE cg.user_id = ? AND t.type = ?";

    private static final String FIND_BY_ID = "SELECT cg.id, cg.conversation_id, cg.user_id\n" +
            "FROM conversation_group cg\n" +
            "WHERE cg.id = ?";

    public ConversationGroupDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<ConversationGroup> findAll() {
        return null;
    }

    @Override
    public ConversationGroup findById(Long id) throws DAOException {
        ConversationGroup group = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return group;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Long create(ConversationGroup entity) throws DAOException {
//        Connection connection = ConnectionPool.getInstance().getConnection();
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
//        } finally {
//            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void update(ConversationGroup entity) {

    }


//    public ConversationGroup findByConversationId(Long conversationId) throws DAOException {
//        ConversationGroup group = null;
//        try {
//            PreparedStatement statement = connection.prepareStatement(SELECT_BY_CONVERSATION_ID);
//
//            statement.setLong(1, conversationId);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                group = builder.build(resultSet);
//            }
//            resultSet.close();
//            statement.close();
//            return group;
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//    }

//    public ConversationGroup findByUserIdAndByConversationId(Long userId, Long conversationId) throws DAOException {
//        ConversationGroup group = null;
//        try {
//            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_AND_CONVERSATION_ID);
//            statement.setLong(1, userId);
//            statement.setLong(2, conversationId);
//
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                group = builder.build(resultSet);
//            }
//            resultSet.close();
//            statement.close();
//            return group;
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//    }

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

    public ConversationGroup findByUserIdAndConversationType(Long userId, Conversation.ConversationType type) throws DAOException {
//        Connection connection = ConnectionPool.getInstance().getConnection();
        ConversationGroup group = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USER_ID_AND_CONVERSATION_TYPE);
            statement.setLong(1, userId);
            statement.setString(2, type.getValue());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                group = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return group;
        } catch (SQLException e) {
            throw new DAOException(e);
//        } finally {
//            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }


}
