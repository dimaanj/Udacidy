package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.MessageBuilder;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDao implements AbstractDao<Long, Message> {
    private EntityBuilder<Message> builder = new MessageBuilder();
    private final Connection connection;

    private static final String INSERT_MESSAGE = "INSERT INTO udacidy.message\n" +
            "(text, creator_id, creation_date_time, conversation_id, image_path)\n" +
            "VALUES (?, ?, ?, ?, ?);\n";

    private static final String SELECT_ALL_BY_CONVERSATION_ID = "SELECT m.id, m.text, m.creator_id, m.creation_date_time, m.conversation_id, image_path\n" +
            "FROM udacidy.message m\n" +
            "  JOIN conversation c\n" +
            "    ON m.conversation_id = c.id\n" +
            "  WHERE c.id = ?";

    private static final String SELECT_LAST_ON_TIME_BY_CONVERSATION_ID = "SELECT m.id, m.text, m.creator_id, m.creation_date_time, m.conversation_id, image_path\n" +
            "FROM conversation с\n" +
            "         JOIN message m\n" +
            "              ON с.id = m.conversation_id\n" +
            "WHERE с.id = ?\n" +
            "ORDER BY m.creation_date_time DESC\n" +
            "LIMIT 1";

    private static final String SELECT_SOME_LAST_MESSAGES_BY_CONVERSATION_ID = "SELECT m.id, m.text, m.creator_id, m.creation_date_time, m.conversation_id, image_path\n" +
            "FROM conversation с\n" +
            "         JOIN message m\n" +
            "              ON с.id = m.conversation_id\n" +
            "WHERE с.id = ?\n" +
            "ORDER BY m.creation_date_time DESC\n" +
            "LIMIT ?";

    private static final String SELECT_SOME_BEFORE_MESSAGE_ID_BY_CONVERSATION_ID = "SELECT m.id, m.text, m.creator_id, m.creation_date_time, m.conversation_id, image_path\n" +
            "FROM conversation сonv\n" +
            "         JOIN message m\n" +
            "              ON сonv.id = m.conversation_id\n" +
            "WHERE сonv.id = ? AND m.id < ?\n" +
            "ORDER BY m.creation_date_time DESC\n" +
            "LIMIT ?";

    private static final String SELECT_ALL_AFTER_MESSAGE_ID_BY_CONVERSATION_ID = "SELECT m.id, m.text, m.creator_id, m.creation_date_time, m.conversation_id, image_path\n" +
            "            FROM conversation сv\n" +
            "                     JOIN message m\n" +
            "                          ON сv.id = m.conversation_id\n" +
            "            WHERE сv.id = ? AND m.id > ?\n" +
            "            ORDER BY m.creation_date_time\n";

    private static final String SELECT_EARLIEST_BY_CONVERSATION_ID = "SELECT m.id, m.text, m.creator_id, m.creation_date_time, m.conversation_id, image_path\n" +
            "FROM conversation сv\n" +
            "         JOIN message m\n" +
            "              ON сv.id = m.conversation_id\n" +
            "WHERE сv.id = ?\n" +
            "ORDER BY m.id\n" +
            "LIMIT 1";

    private static final String UPDATE_MESSAGE_IMAGE_PATH = "UPDATE udacidy.message m\n" +
            "    SET m.image_path = ?\n" +
            "WHERE m.id = ?";

    private static final String COUNT_MESSAGES_BY_CONVERSATION_ID = "SELECT COUNT(*) messages_amount\n" +
            "FROM udacidy.message\n" +
            "    WHERE conversation_id = ?;";

    public MessageDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public Message findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public Long create(Message entity) throws DAOException {
        Long messageId = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement(INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getText());
            statement.setLong(2, entity.getCreatorId());
            Date dateTime = Date
                    .from(entity.getCreationDateTime().atZone(ZoneId.systemDefault())
                            .toInstant());
            Object param = new java.sql.Timestamp(dateTime.getTime());
            statement.setObject(3, param);
            statement.setLong(4, entity.getConversationId());
            statement.setString(5, entity.getImageUrl());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    messageId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }
            statement.close();
            return messageId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Message entity) {
    }

    public List<Message> findByConversationId(Long id) throws DAOException {
        List<Message> messages = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_CONVERSATION_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            messages = new ArrayList<>();
            while (resultSet.next()) {
                Message message = builder.build(resultSet);
                messages.add(message);
            }
            resultSet.close();
            statement.close();
            return messages;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Message findLastOnTimeByConversationId(Long id) throws DAOException {
        Message message = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_LAST_ON_TIME_BY_CONVERSATION_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                message = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return message;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Message> findSomeLastByConversationId(int amount, Long conversationId) throws DAOException {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_SOME_LAST_MESSAGES_BY_CONVERSATION_ID);
            statement.setLong(1, conversationId);
            statement.setLong(2, amount);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = builder.build(resultSet);
                messages.add(message);
            }
            resultSet.close();
            statement.close();
            return messages;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    public List<Message> findSomeLastByConversationIdStartsWithMessageId(int amount, Long conversationId, Long messageId) throws DAOException {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_SOME_BEFORE_MESSAGE_ID_BY_CONVERSATION_ID);
            statement.setLong(1, conversationId);
            statement.setLong(2, messageId);
            statement.setLong(3, amount);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = builder.build(resultSet);
                messages.add(message);
            }
            resultSet.close();
            statement.close();
            return messages;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    public List<Message> findAllAfterMessageId(Long messageId, Long conversationId) throws DAOException {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_AFTER_MESSAGE_ID_BY_CONVERSATION_ID);
            statement.setLong(1, conversationId);
            statement.setLong(2, messageId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = builder.build(resultSet);
                messages.add(message);
            }
            resultSet.close();
            statement.close();
            return messages;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    public Message findEarliestMessageByConversationId(Long conversationId) throws DAOException {
        Message message = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_EARLIEST_BY_CONVERSATION_ID);
            statement.setLong(1, conversationId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                message = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return message;
        } catch (SQLException | DAOException e) {
            throw new DAOException(e);
        }
    }

    public void updateMessageImagePath(String imagePath, Long messageId) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_MESSAGE_IMAGE_PATH);
            statement.setString(1, imagePath);
            statement.setLong(2, messageId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Long countMessagesByConferenceId(Long conversationId) throws DAOException {
        long amount = 0L;
        try {
            PreparedStatement statement = connection.prepareStatement(COUNT_MESSAGES_BY_CONVERSATION_ID);
            statement.setLong(1, conversationId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getLong("messages_amount");
            }
            resultSet.close();
            statement.close();
            return amount;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
