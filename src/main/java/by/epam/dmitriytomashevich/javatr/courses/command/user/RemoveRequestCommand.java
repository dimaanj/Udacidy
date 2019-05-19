package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonSection;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestDataService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ContentServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestDataServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.SectionServiceImpl;
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
    private static final RequestService REQUEST_SERVICE = new RequestServiceImpl();
    private static final RequestDataService REQUEST_FORM_SERVICE = new RequestDataServiceImpl();
    private static final SectionService SECTION_SERVICE = new SectionServiceImpl();
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        List<Section> sectionList = SECTION_SERVICE.findSectionsByConferenceId(conferenceId);

        JsonArray jsonSections = null;
        boolean didSectionsExists;
        String message = null;
        if(!sectionList.isEmpty()) {
            jsonSections = sectionsToJson(sectionList);
            deleteRequest(conferenceId, user, sectionList);
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
            if(didSectionsExists){
                node.putPOJO("conferenceSections", jsonSections);
            }
            node.put("didSectionsExists", didSectionsExists);
            node.put("message", message);

            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }

    private void deleteRequest(Long conferenceId, User user, List<Section> sections) throws LogicException {
        Request request = null;
        for (Section s : sections) {
            if ((request = REQUEST_SERVICE.findBySectionIdAndUserId(s.getId(), user.getId())) != null) {
                REQUEST_SERVICE.deleteRequestWithRequestData(request.getId());
                break;
            }
        }

    }

    private JsonArray sectionsToJson(List<Section> sectionList) throws LogicException {
        JsonArray jsonSections = new JsonArray();
        for(Section s : sectionList){
            JsonSection jsonSection = new JsonSection();
            Content content = CONTENT_SERVICE.findById(s.getContentId());

            jsonSection.setId(s.getId());
            jsonSection.setContent(content.getContent());
            jsonSection.setContentId(content.getId());
            jsonSection.setConferenceId(s.getConferenceId());

            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(jsonSection, JsonSection.class);
            jsonSections.add(element);
        }
        return jsonSections;
    }
}
