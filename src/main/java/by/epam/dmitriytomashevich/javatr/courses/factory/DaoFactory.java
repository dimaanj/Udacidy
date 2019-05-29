package by.epam.dmitriytomashevich.javatr.courses.factory;

import by.epam.dmitriytomashevich.javatr.courses.db.dao.*;

import java.sql.Connection;

public class DaoFactory {
    private final Connection connection;

    public DaoFactory(Connection connection){
        this.connection = connection;
    }

    public ConferenceDao createConferenceDao(){
        return new ConferenceDao(connection);
    }

    public ContentDao createContentDao(){
        return new ContentDao(connection);
    }

    public ConversationGroupDao createConversationGroupDao(){
        return new ConversationGroupDao(connection);
    }

    public ConversationDao createConversationDao(){
        return new ConversationDao(connection);
    }

    public MessageDao createMessageDao(){
        return new MessageDao(connection);
    }

    public RequestDao createRequestDao(){
        return new RequestDao(connection);
    }

    public SectionDao createSectionDao(){
        return new SectionDao(connection);
    }

    public UserDao createUserDao(){
        return new UserDao(connection);
    }

    public RequestFormDao createRequestFormDao(){
        return new RequestFormDao(connection);
    }

}
