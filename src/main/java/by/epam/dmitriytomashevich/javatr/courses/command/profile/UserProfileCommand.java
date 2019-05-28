package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.ConferenceConverter;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.ClientConferencesInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserProfileCommand implements Command {
    private final ConferenceService conferenceService;
    private final SectionService sectionService;
    private final ContentService contentService;
    private final UserService userService;
    private final RequestService requestService;

    public UserProfileCommand(ServiceFactory serviceFactory) {
        conferenceService = serviceFactory.createConferenceService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
        userService = serviceFactory.createUserService();
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);

        List<Conference> conferences = conferenceService.findAllConferencesAsUserRequestsByUserId(user.getId());
        for (Conference c : conferences) {
            ClientConferencesInitializer initializer = new ClientConferencesInitializer.Builder(user, c)
                    .withContentService(contentService)
                    .withRequestService(requestService)
                    .withSectionService(sectionService)
                    .withUserService(userService)
                    .build();
            initializer.init();
        }
        if (!conferences.isEmpty()) {
            content.setRequestAttribute("conferences", conferences);
        }

        doLogicWhenRequestWasRemoved(content);
        doLogicWhenRequestWasSent(content, user);
        return Optional.of(ActionNames.PROFILE);
    }

    private void doLogicWhenRequestWasSent(SessionRequestContent content, User user) throws LogicException {
        String requestWasSentParam = content.getParameter("requestWasSent");
        String conferenceIdToShowAsString = content.getParameter("conferenceIdToShow");
        if (requestWasSentParam != null && conferenceIdToShowAsString != null) {
            Long conferenceId = Long.valueOf(conferenceIdToShowAsString);
            Conference conference = conferenceService.getById(conferenceId);
            ClientConferencesInitializer initializer = new ClientConferencesInitializer.Builder(user, conference)
                    .withContentService(contentService)
                    .withRequestService(requestService)
                    .withSectionService(sectionService)
                    .withUserService(userService)
                    .build();
            initializer.init();

            List<Request> requests = requestService.findAllByUserIdAndConferenceId(user.getId(), conferenceId);
            List<Long> sectionsRequestIds = new ArrayList<>();
            if (requests != null && !requests.isEmpty()) {
                sectionsRequestIds = requests.stream()
                        .map(Request::getSectionId)
                        .collect(Collectors.toList());
            }
            JsonArray jsonSectionRequestIds = new JsonArray();
            for (Long id : sectionsRequestIds) {
                jsonSectionRequestIds.add(id);
            }

            JsonConference jsonConference = new ConferenceConverter().convert(conference);
            JsonElement jsonConferenceElement = new Gson().toJsonTree(jsonConference, JsonConference.class);

            content.setRequestAttribute("requestWasSentMessage", "Your request was successfully sent!");
            content.setRequestAttribute("conferenceToShow", jsonConferenceElement);
            content.setRequestAttribute("requestSectionsIds", jsonSectionRequestIds);
        }
    }

    private void doLogicWhenRequestWasRemoved(SessionRequestContent content){
        String removeRequestMessage = content.getParameter("removeRequestMessage");
        if (removeRequestMessage != null) {
            content.setRequestAttribute("removeRequestMessage", removeRequestMessage);
        }
    }
}
