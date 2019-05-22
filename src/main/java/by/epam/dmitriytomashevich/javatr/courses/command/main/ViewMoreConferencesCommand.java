package by.epam.dmitriytomashevich.javatr.courses.command.main;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonSection;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
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

public class ViewMoreConferencesCommand implements Command {
    private final ConferenceService conferenceService;
    private final ContentService contentService;
    private final UserService userService;
    private final SectionService sectionService;
    private final RequestService requestService;

    public ViewMoreConferencesCommand(ServiceFactory serviceFactory){
        conferenceService = serviceFactory.createConferenceService();
        contentService = serviceFactory.createContentService();
        userService = serviceFactory.createUserService();
        sectionService = serviceFactory.createSectionService();
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long lastConferenceId = Long.valueOf(content.getParameter("lastConferenceId"));
        List<Conference> conferenceList = conferenceService.findSomeOlderStartsWithConversationId(lastConferenceId);
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);

        JsonArray jsonConferencesList = new JsonArray();
        for (Conference c : conferenceList) {
            User author = userService.findById(c.getAuthorId());
            List<Section> sections = sectionService.findSectionsByConferenceId(c.getId());

            for(Section s : sections){
                s.setContent(contentService.findById(s.getContentId()));
            }
            c.setContent(contentService.findById(c.getContentId()));
            c.setContentId(c.getContentId());
            c.setAuthor(author);
            c.setSections(sections);

            JsonConference conference = new ConferenceConverter().convert(c);
            if(!user.isAdmin()){
                conference.setRequestAlreadySent(
                        isRequestAlreadySentRequest(user, sections));
            }

            JsonElement element = new Gson().toJsonTree(conference, JsonConference.class);

            jsonConferencesList.add(element);
        }

        boolean hideViewMoreButton = false;
        if(jsonConferencesList.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
                conferenceService.getTheOldest().getId().equals(conferenceList.get(0).getId())){
            hideViewMoreButton = true;
        }

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");

            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.putPOJO("conferences", jsonConferencesList);
            node.put("hideViewMoreButton", hideViewMoreButton);

            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }

    private boolean isRequestAlreadySentRequest(User user, List<Section> sections) throws LogicException {
        for(Section s : sections){
            if (requestService.findBySectionIdAndUserId(s.getId(), user.getId()) != null){
                return true;
            }
        }
        return false;
    }
}
