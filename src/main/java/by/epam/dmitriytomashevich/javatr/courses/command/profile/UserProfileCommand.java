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

//    private void doLogicWhenRequestWasSent(SessionRequestContent content, User user) throws LogicException {
//        String requestWasSentParam = content.getParameter("requestWasSent");
//        String conferenceIdToShowAsString = content.getParameter("conferenceIdToShow");
//        if (requestWasSentParam != null && conferenceIdToShowAsString != null) {
//            Long conferenceId = Long.valueOf(conferenceIdToShowAsString);
//            Conference conference = conferenceService.getById(conferenceId);
//            ClientConferencesInitializer initializer = new ClientConferencesInitializer.Builder(user, conference)
//                    .withContentService(contentService)
//                    .withRequestService(requestService)
//                    .withSectionService(sectionService)
//                    .withUserService(userService)
//                    .build();
//            initializer.init();
//
//            List<Request> requests = requestService.findAllByUserIdAndConferenceId(user.getId(), conferenceId);
//            List<Long> sectionsRequestIds = new ArrayList<>();
//            if (requests != null && !requests.isEmpty()) {
//                sectionsRequestIds = requests.stream()
//                        .map(Request::getSectionId)
//                        .collect(Collectors.toList());
//            }
//            JsonArray jsonSectionRequestIds = new JsonArray();
//            for (Long id : sectionsRequestIds) {
//                jsonSectionRequestIds.add(id);
//            }
//
//            JsonConference jsonConference = new ConferenceConverter().convert(conference);
//            JsonElement jsonConferenceElement = new Gson().toJsonTree(jsonConference, JsonConference.class);
//
//            content.setRequestAttribute("requestWasSentMessage", "Your request was successfully sent!");
//            content.setRequestAttribute("conferenceToShow", jsonConferenceElement);
//            content.setRequestAttribute("requestSectionsIds", jsonSectionRequestIds);
//        }
//    }
}
