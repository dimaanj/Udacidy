package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestDataService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SendRequestCommand implements Command {
    private final RequestDataService requestDataService;
    private final RequestService requestService;
    private final SectionService sectionService;

    public SendRequestCommand(ServiceFactory serviceFactory){
        requestDataService = serviceFactory.createRequestDataService();
        requestService = serviceFactory.createRequestService();
        sectionService = serviceFactory.createSectionService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(Parameter.REQUEST_SECTIONS_IDS), List.class);
        Section section = sectionService.findById(Long.valueOf(jsonSectionsIds.get(0)));

        boolean isSectionExists;
        String message = null;
        if(section != null) {
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
            message = "Your request was sent successfully!" ;
            isSectionExists = true;
        }else {
            message = "Sorry, conference hasn't already exist!";
            isSectionExists = false;
        }

        try {
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put("isSectionExists", isSectionExists);
            node.put("message", message);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
