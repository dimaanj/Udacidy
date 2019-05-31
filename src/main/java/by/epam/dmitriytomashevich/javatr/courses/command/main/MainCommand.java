package by.epam.dmitriytomashevich.javatr.courses.command.main;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;

import java.util.Optional;

public class MainCommand implements Command {
    private final ConferenceService conferenceService;

    public MainCommand(ServiceFactory serviceFactory){
        conferenceService = serviceFactory.createConferenceService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(ParameterNames.USER);
        Long numberOfConferences = conferenceService.countNumberOfConferences();
        content.setRequestAttribute("conferencesNumber", numberOfConferences);
        String action;
        if(user.isAdmin()){
            action = ActionNames.CONTENT_EDITING;
        }else {
            action = ActionNames.CONFERENCES;
        }
        return Optional.of(action);
    }
}
