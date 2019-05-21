package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conversation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConversationBuilder implements EntityBuilder<Conversation> {
    @Override
    public Conversation build(ResultSet resultSet) throws DAOException {
        Conversation conversation = new Conversation();
        try {
            conversation.setId(resultSet.getLong("id"));
            conversation.setCreateDate(resultSet.getDate("date_creation").toLocalDate());
            conversation.setType(Conversation.ConversationType.fromValue(resultSet.getString("type")));
            return conversation;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
