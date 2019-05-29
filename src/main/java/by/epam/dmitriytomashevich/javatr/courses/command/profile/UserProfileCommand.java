package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;

import java.util.*;

public class UserProfileCommand implements Command {
    private final ConferenceService conferenceService;
    private final RequestService requestService;

    public UserProfileCommand(ServiceFactory serviceFactory) {
        conferenceService = serviceFactory.createConferenceService();
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<Map.Entry<Request, Conference>> requestsWithConferences = new ArrayList<>();
        List<Request> requests = requestService.findByUserId(user.getId());
        for (Request r : requests) {
            Conference c = conferenceService.getById(r.getConferenceId());
            requestsWithConferences.add(new AbstractMap.SimpleEntry<>(r, c));
        }
        if (!requestsWithConferences.isEmpty()) {
            content.setRequestAttribute("requestsWithConferences", requestsWithConferences);
        }

        return Optional.of(ActionNames.PROFILE);
    }
}
