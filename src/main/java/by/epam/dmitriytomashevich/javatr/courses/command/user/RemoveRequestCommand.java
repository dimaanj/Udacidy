package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonSection;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.SectionConverter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class RemoveRequestCommand implements Command {
    private final RequestService requestService;
    private final SectionService sectionService;
    private final ContentService contentService;

    public RemoveRequestCommand(ServiceFactory serviceFactory){
        requestService = serviceFactory.createRequestService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        List<Section> sectionList = sectionService.findSectionsByConferenceId(conferenceId);

        boolean didSectionsExists;
        String message = null;
        if(!sectionList.isEmpty()) {
            deleteRequest(conferenceId, user);
            message = "Your request was removed successfully!";
            didSectionsExists = true;
        }else {
            message = "You have done something wrong. Maybe conference had already removed by admins.";
            didSectionsExists = false;
        }

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put("didSectionsExists", didSectionsExists);
            node.put("message", message);

            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }

    private void deleteRequest(Long conferenceId, User user) throws LogicException {
        Request request = requestService.findByUserIdAndConferenceId(user.getId(), conferenceId);
        requestService.deleteRequestWithRequestData(request.getId());
    }
}
