package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.ClientConferencesInitializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

public class ConferencesCommand implements Command {
    private final ConferenceService conferenceService;
    private final UserService userService;
    private final SectionService sectionService;
    private final ContentService contentService;
    private final RequestService requestService;

    public ConferencesCommand(ServiceFactory serviceFactory) {
        conferenceService = serviceFactory.createConferenceService();
        userService = serviceFactory.createUserService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<Conference> conferences = conferenceService.findSomeLastConferences(Parameter.CONFERENCES_UPDATE_AMOUNT);
        ClientConferencesInitializer loader = new ClientConferencesInitializer(
                new ClientConferencesInitializer
                        .Builder(user, conferences)
                        .withContentService(contentService)
                        .withRequestService(requestService)
                        .withSectionService(sectionService)
                        .withUserService(userService)
        );
        loader.init();
        content.setRequestAttribute("conferences", conferences);
        if (conferences.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
                conferenceService.getTheOldest().getId().equals(conferences.get(conferences.size() - 1).getId())) {
            content.setRequestAttribute("hideViewMoreButton", Boolean.TRUE);
        }
        return Optional.of(ActionNames.CONFERENCES);
    }
}
