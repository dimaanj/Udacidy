package by.epam.dmitriytomashevich.javatr.courses.command.user;

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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SendClientRequestCommand implements Command {
    private final RequestService requestService;
    private final SectionService sectionService;
    private final RequestFormService requestFormService;
    private final ConferenceService conferenceService;
    private final UserService userService;
    private final ContentService contentService;

    public SendClientRequestCommand(ServiceFactory serviceFactory) {
        requestService = serviceFactory.createRequestService();
        sectionService = serviceFactory.createSectionService();
        requestFormService = serviceFactory.createRequestFormService();
        conferenceService = serviceFactory.createConferenceService();
        userService = serviceFactory.createUserService();
        contentService = serviceFactory.createContentService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(Parameter.REQUEST_SECTIONS_IDS), List.class);

        Long conferenceId = sectionService.findById(Long.valueOf(jsonSectionsIds.get(0))).getConferenceId();

        Request request = new Request();
        request.setUserId(user.getId());
        request.setCreationDateTime(LocalDateTime.now());
        request.setRequestStatus(RequestStatus.SHIPPED);
        request.setConferenceId(conferenceId);
        Long requestId = requestService.create(request);

        for (String stringId : jsonSectionsIds) {
            Section section = sectionService.findById(Long.valueOf(stringId));
            RequestForm requestForm = new RequestForm();
            requestForm.setSectionId(section.getId());
            requestForm.setRequestId(requestId);
            requestFormService.create(requestForm);
        }

        Conference conference = conferenceService.getById(conferenceId);
        conference.setAuthor(userService.findById(conference.getAuthorId()));
        List<Section> sections = sectionService.findSectionsByConferenceId(conferenceId);
        for(Section s: sections){
            s.setContent(contentService.findById(s.getContentId()));
        }
        conference.setSections(sections);
        conference.setContent(contentService.findById(conference.getContentId()));
        JsonConference jsonConference = new ConferenceConverter().convert(conference);

        List<Long> requestSectionsIds = jsonSectionsIds.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        JsonElement jsonConferenceElement = new Gson().toJsonTree(jsonConference, JsonConference.class);

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put("message", "Your request was successfully sent!");
            node.putPOJO("conferenceToShow", jsonConferenceElement);
            node.putPOJO("requestSectionsIds", requestSectionsIds);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
