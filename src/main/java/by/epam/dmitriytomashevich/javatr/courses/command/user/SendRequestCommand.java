package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestDataService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestDataServiceImpl;
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

public class SendRequestCommand implements Command {
    private static final RequestDataService REQUEST_FORM_SERVICE = new RequestDataServiceImpl();
    private static final RequestService REQUEST_SERVICE = new RequestServiceImpl();
    private static final SectionService SECTION_SERVICE = new SectionServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(Parameter.REQUEST_SECTIONS_IDS), List.class);
        Section section = SECTION_SERVICE.findById(Long.valueOf(jsonSectionsIds.get(0)));

        boolean isSectionExists;
        String message = null;
        if(section != null) {
            Request request = new Request();
            request.setUserId(user.getId());
            request.setCreationDateTime(LocalDateTime.now());
            Long requestId = REQUEST_SERVICE.create(request);

            for (String stringId : jsonSectionsIds) {
                RequestData requestData = new RequestData();
                requestData.setSectionId(Long.valueOf(stringId));
                requestData.setRequestId(requestId);
                REQUEST_FORM_SERVICE.create(requestData);
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
