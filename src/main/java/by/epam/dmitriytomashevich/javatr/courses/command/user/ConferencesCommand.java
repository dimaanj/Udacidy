package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.ClientConferencesInitializer;

import java.util.List;
import java.util.Optional;
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
        for (Conference c : conferences) {
            ClientConferencesInitializer initializer = new ClientConferencesInitializer.Builder(user, c)
                    .withContentService(contentService)
                    .withRequestService(requestService)
                    .withSectionService(sectionService)
                    .withUserService(userService)
                    .build();
            initializer.init();
        }

        content.setRequestAttribute("conferences", conferences);
        if (conferences.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
                conferenceService.getTheOldest().getId().equals(conferences.get(conferences.size() - 1).getId())) {
            content.setRequestAttribute("hideViewMoreButton", Boolean.TRUE);
        }
        return Optional.of(ActionNames.CONFERENCES);
    }
}
