package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestData;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestFormService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestFormServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestServiceImpl;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SendRequestCommand implements Command {
    private static final RequestFormService REQUEST_FORM_SERVICE = new RequestFormServiceImpl();
    private static final RequestService REQUEST_SERVICE = new RequestServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<String> jsonSectionsIds = new Gson().fromJson(content.getParameter(Parameter.REQUEST_SECTIONS_IDS), List.class);

        Request request = new Request();
        request.setUserId(user.getId());
        request.setCreationDateTime(LocalDateTime.now());
        Long requestId = REQUEST_SERVICE.create(request);

        for(String stringId : jsonSectionsIds){
            RequestData requestData = new RequestData();
            requestData.setSectionId(Long.valueOf(stringId));
            requestData.setRequestId(requestId);
            REQUEST_FORM_SERVICE.create(requestData);
        }

        try {
            content.getResponse().setContentType("text/plain");
            PrintWriter writer = content.getResponse().getWriter();
            writer.print("Your request was sent successfully!");
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
