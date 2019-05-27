package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.ConferenceConverter;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.ClientConferencesInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProfileCommand implements Command {
    private final ConferenceService conferenceService;
    private final SectionService sectionService;
    private final ContentService contentService;
    private final UserService userService;
    private final RequestService requestService;
    private final RequestDataService requestDataService;

    public ProfileCommand(ServiceFactory serviceFactory) {
        conferenceService = serviceFactory.createConferenceService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
        userService = serviceFactory.createUserService();
        requestService = serviceFactory.createRequestService();
        requestDataService = serviceFactory.createRequestDataService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
//        if(content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)){
//            return Optional.of(ActionNames.PROFILE);
//        }else {
            User user = (User) content.getSession(false).getAttribute(Parameter.USER);
            List<Conference> conferences = conferenceService.findAllConferencesAsUserRequestsByUserId(user.getId());
            ClientConferencesInitializer loader = new ClientConferencesInitializer(
                    new ClientConferencesInitializer
                            .Builder(user, conferences)
                            .withContentService(contentService)
                            .withRequestService(requestService)
                            .withSectionService(sectionService)
                            .withUserService(userService)
            );
            loader.init();
            if (!conferences.isEmpty()) {
                content.setRequestAttribute("conferences", conferences);
            }

            String removeRequestMessage = content.getParameter("removeRequestMessage");
            if (removeRequestMessage != null) {
                content.setRequestAttribute("removeRequestMessage", removeRequestMessage);
            }

            String requestWasSentParam = content.getParameter("requestWasSent");
            String conferenceIdToShowAsString = content.getParameter("conferenceIdToShow");
            if (requestWasSentParam != null && conferenceIdToShowAsString != null) {
                Long conferenceId = Long.valueOf(conferenceIdToShowAsString);

                Conference conference = conferenceService.getById(conferenceId);
                Request request = requestService.findByUserIdAndConferenceId(user.getId(), conferenceId);


                List<Section> sectionList = sectionService.findSectionsByConferenceId(conferenceId);
                for (Section s : sectionList) {
                    s.setContent(contentService.findById(s.getContentId()));
                }
                User author = userService.findById(conference.getAuthorId());
                Content conferenceContent = contentService.findById(conference.getContentId());

                conference.setRequestStatus(request.getRequestStatus());
                conference.setContent(conferenceContent);
                conference.setSections(sectionList);
                conference.setAuthor(author);

                List<Long> sectionsRequestIds =
                        requestDataService.findAllByRequestId(request.getId())
                                .stream()
                                .map(RequestData::getSectionId)
                                .collect(Collectors.toList());
                JsonArray jsonArray = new JsonArray();
                for (Long id : sectionsRequestIds) {
                    jsonArray.add(id);
                }

                JsonConference jsonConference = new ConferenceConverter().convert(conference);
                JsonElement jsonElement = new Gson().toJsonTree(jsonConference, JsonConference.class);

                content.setRequestAttribute("requestWasSentMessage", "Your request was successfully sent!");
                content.setRequestAttribute("conferenceToShow", jsonElement);
                content.setRequestAttribute("requestSectionsIds", jsonArray);
            }
            return Optional.of(ActionNames.PROFILE);
//        }
    }
}
