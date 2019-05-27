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
    private final RequestDataService requestDataService;
    private final SectionService sectionService;

    public SendClientRequestCommand(ServiceFactory serviceFactory) {
        requestService = serviceFactory.createRequestService();
        requestDataService = serviceFactory.createRequestDataService();
        sectionService = serviceFactory.createSectionService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(Parameter.REQUEST_SECTIONS_IDS), List.class);

//        boolean isPositiveResult;
//        String message = null;
//        Section section = sectionService.findById(Long.valueOf(jsonSectionsIds.get(0)));
//        if (requestService.findByUserIdAndConferenceId(section.getConferenceId(), user.getId()) != null) {
//            message = "Sorry request was already sent! You can't send request to conference more than one time";
//            isPositiveResult = false;
//        } else {

        Request request = new Request();
        request.setUserId(user.getId());
        request.setCreationDateTime(LocalDateTime.now());
        request.setRequestStatus(RequestStatus.SHIPPED);
        Long requestId = requestService.create(request);

        for (String stringId : jsonSectionsIds) {
            RequestData requestData = new RequestData();
            requestData.setSectionId(Long.valueOf(stringId));
            requestData.setRequestId(requestId);
            requestDataService.create(requestData);
        }
        Section section = sectionService.findById(Long.valueOf(jsonSectionsIds.get(0)));
//            isPositiveResult = true;
//            message = "Your request was sent successfully!";
//        }

//        try {
//            content.getResponse().setContentType("application/json;charset=UTF-8");
//            final JsonNodeFactory factory = JsonNodeFactory.instance;
//            final ObjectNode node = factory.objectNode();
//
//            node.put("isPositiveResult", isPositiveResult);
//            node.put("message", message);
//            PrintWriter writer = content.getResponse().getWriter();
//            writer.print(node);
//        } catch (IOException e) {
//            throw new LogicException(e);
//        }

//        '/udacidy/profile?requestWasSent=true&conferenceIdToShow='+col.parentElement.getAttribute('id')

        return Optional.of(ActionNames.PROFILE_ACTION + "?requestWasSent=true&conferenceIdToShow=" +section.getConferenceId());
    }
}
