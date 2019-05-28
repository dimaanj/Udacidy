package by.epam.dmitriytomashevich.javatr.courses.db.builder;

import by.epam.dmitriytomashevich.javatr.courses.domain.ConversationGroup;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConversationGroupBuilder implements EntityBuilder<ConversationGroup> {
    @Override
    public ConversationGroup build(ResultSet resultSet) throws DAOException, SQLException {
        ConversationGroup group = new ConversationGroup();
        group.setId(resultSet.getLong("id"));
        group.setConversationId(resultSet.getLong("conversation_id"));
        group.setUserId(resultSet.getLong("user_id"));
        return group;
    }
}
