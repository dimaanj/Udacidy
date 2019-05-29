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

import java.util.*;

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
        List<Map.Entry<Conference, Request>> conferencesWithRequests = new ArrayList<>();
        for (Conference c : conferences) {
            c.setAuthor(userService.findById(c.getAuthorId()));
            c.setContent(contentService.findById(c.getContentId()));
            List<Section> sections = sectionService.findSectionsByConferenceId(c.getId());
            for(Section s: sections) {
                s.setContent(contentService.findById(s.getContentId()));
            }
            c.setSections(sections);
            Request request = requestService.findByUserIdAndConferenceId(user.getId(), c.getId());
            if (request != null) {
                conferencesWithRequests.add(new AbstractMap.SimpleEntry<>(c, request));
            } else {
                conferencesWithRequests.add(new AbstractMap.SimpleEntry<>(c, new Request()));
            }
        }

        Long numberOfConferences = conferenceService.countNumberOfConferences();
        content.setRequestAttribute("conferencesNumber", numberOfConferences);
        content.setRequestAttribute("conferencesWithRequests", conferencesWithRequests);
        if (conferences.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
                conferenceService.getTheOldest().getId().equals(conferences.get(conferences.size() - 1).getId())) {
            content.setRequestAttribute("hideViewMoreButton", Boolean.TRUE);
        }
        return Optional.of(ActionNames.CONFERENCES);
    }
}
