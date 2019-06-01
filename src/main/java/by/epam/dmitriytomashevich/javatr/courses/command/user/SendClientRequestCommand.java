package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestFormServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.SectionServiceImpl;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SendClientRequestCommand implements Command {
    private final RequestService requestService = new RequestServiceImpl();
    private final SectionService sectionService = new SectionServiceImpl();
    private final RequestFormService requestFormService = new RequestFormServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(ParameterNames.USER);
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(ParameterNames.REQUEST_SECTIONS_IDS), List.class);

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

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
