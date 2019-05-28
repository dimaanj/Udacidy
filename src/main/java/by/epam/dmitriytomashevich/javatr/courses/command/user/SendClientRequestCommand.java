package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SendClientRequestCommand implements Command {
    private final RequestService requestService;
    private final SectionService sectionService;

    public SendClientRequestCommand(ServiceFactory serviceFactory) {
        requestService = serviceFactory.createRequestService();
        sectionService = serviceFactory.createSectionService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(Parameter.REQUEST_SECTIONS_IDS), List.class);

        Long conferenceId = sectionService.findById(Long.valueOf(jsonSectionsIds.get(0))).getConferenceId();
        for (String stringId : jsonSectionsIds) {
            Section section = sectionService.findById(Long.valueOf(stringId));

            Request request = new Request();
            request.setUserId(user.getId());
            request.setCreationDateTime(LocalDateTime.now());
            request.setRequestStatus(RequestStatus.SHIPPED);
            request.setConferenceId(section.getConferenceId());
            request.setSectionId(section.getId());

            requestService.create(request);
        }

        return Optional.of(ActionNames.PROFILE_ACTION + "?requestWasSent=true&conferenceIdToShow=" + conferenceId);
    }
}
