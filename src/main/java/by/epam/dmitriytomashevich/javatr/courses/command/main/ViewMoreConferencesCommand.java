package by.epam.dmitriytomashevich.javatr.courses.command.main;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConferenceServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ContentServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class ViewMoreConferencesCommand implements Command {
    private static final ConferenceService CONFERENCE_SERVICE = new ConferenceServiceImpl();
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long lastConferenceId = Long.valueOf(content.getParameter("lastConferenceId"));
        List<Conference> conferenceList = CONFERENCE_SERVICE.findSomeOlderStartsWithConversationId(lastConferenceId);

        JsonArray jsonConferencesList = new JsonArray();
        for (Conference c : conferenceList) {
            User author = USER_SERVICE.findById(c.getAuthorId());

            JsonConference conference = new JsonConference();
            conference.setId(c.getId());
            conference.setAuthorId(author.getId());
            conference.setContent(CONTENT_SERVICE.findById(c.getContentId()).getContent());
            conference.setContentId(c.getContentId());
            conference.setAuthorFirstName(author.getFirstName());
            conference.setAuthorLastName(author.getLastName());

            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(conference, JsonConference.class);
            jsonConferencesList.add(element);
        }

        boolean hideViewMoreButton = false;
        if(jsonConferencesList.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
                CONFERENCE_SERVICE.getTheOldest().getId().equals(conferenceList.get(0).getId())){
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
}
