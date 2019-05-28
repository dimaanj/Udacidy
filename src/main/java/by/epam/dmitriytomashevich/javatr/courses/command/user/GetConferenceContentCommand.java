package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.ConferenceConverter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetConferenceContentCommand implements Command {
    private final ConferenceService conferenceService;
    private final ContentService contentService;
    private final RequestService requestService;
    private final SectionService sectionService;
    private final UserService userService;

    public GetConferenceContentCommand(ServiceFactory serviceFactory){
        conferenceService = serviceFactory.createConferenceService();
        contentService = serviceFactory.createContentService();
        requestService = serviceFactory.createRequestService();
        sectionService = serviceFactory.createSectionService();
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        User client = (User) content.getSession(false).getAttribute(Parameter.USER);

        Conference conference = conferenceService.getById(conferenceId);
        List<Section> sectionList = sectionService.findSectionsByConferenceId(conferenceId);
        for(Section s : sectionList){
            s.setContent(contentService.findById(s.getContentId()));
        }

        List<Request> requests = requestService.findAllByUserIdAndConferenceId(client.getId(), conferenceId);
        List<Long> sectionsRequestIds = new ArrayList<>();
        if(requests != null && !requests.isEmpty()){
            conference.setRequestSent(true);
            conference.setRequestStatus(requestService.findById(requests.get(0).getId()).getRequestStatus());

            sectionsRequestIds = requests.stream()
                    .map(Request::getSectionId)
                    .collect(Collectors.toList());
        }
        conference.setContent(contentService.findById(conference.getContentId()));
        conference.setSections(sectionList);
        conference.setAuthor(userService.findById(conference.getAuthorId()));

        JsonArray jsonSectionRequestIds = new JsonArray();
        for(Long id : sectionsRequestIds){
            jsonSectionRequestIds.add(id);
        }

        JsonConference jsonConference = new ConferenceConverter().convert(conference);
        JsonElement jsonConferenceElement = new Gson().toJsonTree(jsonConference, JsonConference.class);

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.putPOJO("conference", jsonConferenceElement);
            node.putPOJO("requestSectionsIds", jsonSectionRequestIds);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
