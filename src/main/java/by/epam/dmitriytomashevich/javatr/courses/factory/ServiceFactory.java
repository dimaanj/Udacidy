package by.epam.dmitriytomashevich.javatr.courses.factory;

import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.*;

public class ServiceFactory {
    private final DaoFactory daoFactory;

    public ServiceFactory(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    public ConferenceService createConferenceService(){
        return new ConferenceServiceImpl(daoFactory);
    }

    public ContentService createContentService(){
        return new ContentServiceImpl(daoFactory);
    }

    public ConversationGroupService createConversationGroupService(){
        return new ConversationGroupServiceImpl(daoFactory);
    }

    public ConversationService createConversationService(){
        return new ConversationServiceImpl(daoFactory);
    }

    public MessageService createMessageService(){
        return new MessageServiceImpl(daoFactory);
    }

    public RequestService createRequestService(){
        return new RequestServiceImpl(daoFactory);
    }

    public SectionService createSectionService(){
        return new SectionServiceImpl(daoFactory);
    }

    public UserService createUserService(){
        return new UserServiceImpl(daoFactory);
    }

    public RequestFormService createRequestFormService(){
        return new RequestFormServiceImpl(daoFactory);
    }


}
