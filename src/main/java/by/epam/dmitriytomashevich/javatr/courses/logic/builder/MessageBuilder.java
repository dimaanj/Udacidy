package by.epam.dmitriytomashevich.javatr.courses.logic.builder;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageBuilder implements EntityBuilder<Message> {
    @Override
    public Message build(ResultSet resultSet) throws DAOException {
        Message message = new Message();
        try {
            message.setId(resultSet.getLong("id"));
            message.setText(resultSet.getString("text"));
            message.setCreatorId(resultSet.getLong("creator_id"));
            message.setCreationDateTime(
                    resultSet.getTimestamp("creation_date_time").toLocalDateTime()
            );
            message.setConversationId(resultSet.getLong("conversation_id"));
            message.setImageUrl(resultSet.getString("image_path"));
            return message;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
