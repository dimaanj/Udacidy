package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConferenceServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestServiceImpl;

import java.util.*;

public class ProfileCommand implements Command {
    private final ConferenceService conferenceService = new ConferenceServiceImpl();
    private final RequestService requestService = new RequestServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(ParameterNames.USER);
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
