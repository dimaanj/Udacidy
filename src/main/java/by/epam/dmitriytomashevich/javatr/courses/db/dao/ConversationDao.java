package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.ConversationBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConversationDao implements AbstractDao<Long, Conversation> {
    private EntityBuilder<Conversation> builder = new ConversationBuilder();
    private final Connection connection;

    private static final String INSERT_CONVERSATION_GROUP = "INSERT INTO udacidy.conversation (date_creation) " +
            "VALUES (?)";
    private static final String INSERT_CONVERSATION_TYPE = "INSERT INTO udacidy.conversation_type (id, type)\n" +
            "VALUES(LAST_INSERT_ID(), ?)";


    private static final String SELECT_ALL_CONVERSATIONS = "SELECT c.id, c.date_creation, ct.type\n" +
            "FROM conversation c JOIN conversation_type ct\n" +
            "            ON c.id = ct.id";

    private static final String SELECT_ALL_CONVERSATIONS_BY_TYPE = "SELECT c.id, c.date_creation, ct.type\n" +
            "FROM conversation c\n" +
            "         JOIN conversation_type ct\n" +
            "              ON c.id = ct.id\n" +
            "WHERE ct.type = ?";

    private static final String SELECT_BY_ID = "SELECT c.id, c.date_creation, ct.type\n" +
            "FROM conversation c\n" +
            "         JOIN conversation_type ct\n" +
            "              ON c.id = ct.id\n" +
            "WHERE c.id = ?";

    private static final String SELECT_BY_USER_ID = "SELECT c.id, c.date_creation, ct.type\n" +
            "FROM conversation c\n" +
            "         JOIN conversation_type ct\n" +
            "              ON c.id = ct.id\n" +
            "    JOIN conversation_group cg \n" +
            "        ON c.id = cg.conversation_id\n" +
            "    JOIN user u \n" +
            "        ON cg.user_id = u.id\n" +
            "WHERE u.id = ?";

//    private static final String SELECT_BY_USER_ID_AND_CONVERSATION_TYPE = "SELECT c.id, c.date_creation, ct.type\n" +
//            "FROM conversation c\n" +
//            "         JOIN conversation_type ct\n" +
//            "              ON c.id = ct.id\n" +
//            "    JOIN conversation_group cg \n" +
//            "        ON c.id = cg.conversation_id\n" +
//            "    JOIN user u \n" +
//            "        ON cg.user_id = u.id\n" +
//            "WHERE u.id = ? AND ct.type = ?";

    private static final String SELECT_BY_MESSAGE_ID = "SELECT c.id, c.date_creation, ct.type\n" +
            "FROM message m\n" +
            "    JOIN conversation c\n" +
            "        ON m.conversation_id = c.id\n" +
            "         JOIN conversation_type ct\n" +
            "              ON c.id = ct.id\n" +
            "    WHERE m.id = ?";

    private static final String DELETE_CONVERSATION_TYPE_BY_ID = "DELETE FROM conversation_type\n" +
            "    WHERE id = ?";

    private static final String DELETE_BY_ID = "DELETE FROM conversation\n" +
            "    WHERE id = ?";

    public ConversationDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Conversation> findAll() throws DAOException {
        List<Conversation> conversations;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONVERSATIONS);
            ResultSet resultSet = statement.executeQuery();
            conversations = new ArrayList<>();
            while (resultSet.next()) {
                Conversation conversation = builder.build(resultSet);
                conversations.add(conversation);
            }
            resultSet.close();
            statement.close();
            return conversations;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Conversation findById(Long id) throws DAOException {
        Conversation conversation = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                conversation = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return conversation;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CONVERSATION_TYPE_BY_ID);
            statement.setLong(1, id);
            statement.execute();

            statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Long create(Conversation entity) throws DAOException {
        Long conversationId = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement(INSERT_CONVERSATION_GROUP, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(entity.getCreateDate()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conversationId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }

            statement = connection.prepareStatement(INSERT_CONVERSATION_TYPE);
            statement.setString(1, entity.getType().getValue());

            statement.execute();
            statement.close();
            return conversationId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Conversation entity) {

    }

    public List<Conversation> findAllByUserId(Long id) throws DAOException {
        List<Conversation> conversations;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            conversations = new ArrayList<>();
            while (resultSet.next()) {
                Conversation conversation = builder.build(resultSet);
                conversations.add(conversation);
            }
            resultSet.close();
            statement.close();
            return conversations;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Conversation findByMessageId(Long messageId) throws DAOException {
        Conversation conversation = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_MESSAGE_ID);
            statement.setLong(1, messageId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                conversation = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return conversation;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

//    public Conversation findUserConversationByType(Long userId, Conversation.ConversationType type) throws DAOException {
//        Conversation conversation = null;
//        try {
//            PreparedStatement statement = connection.prepareStatement(SELECT_BY_USER_ID_AND_CONVERSATION_TYPE);
//            statement.setLong(1, userId);
//            statement.setString(2, type.getValue());
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                conversation = builder.build(resultSet);
//            }
//            resultSet.close();
//            statement.close();
//            return conversation;
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//    }

    public List<Conversation> findAllConversationsByType(Conversation.ConversationType type) throws DAOException {
        List<Conversation> conversations = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CONVERSATIONS_BY_TYPE);
            statement.setString(1, type.getValue());
            ResultSet resultSet = statement.executeQuery();
            conversations = new ArrayList<>();
            while (resultSet.next()) {
                Conversation conversation = builder.build(resultSet);
                conversations.add(conversation);
            }
            resultSet.close();
            statement.close();
            return conversations;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

}
