package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.ConferenceConverter;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.ClientConferencesInitializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class GetConferencesCommand implements Command {
    private final ConferenceService conferenceService;
    private final UserService userService;
    private final SectionService sectionService;
    private final ContentService contentService;
    private final RequestService requestService;


    public GetConferencesCommand(ServiceFactory serviceFactory){
        conferenceService = serviceFactory.createConferenceService();
        userService = serviceFactory.createUserService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<Conference> conferences = conferenceService.findSomeLastConferences(Parameter.CONFERENCES_UPDATE_AMOUNT);
        ClientConferencesInitializer loader = new ClientConferencesInitializer(
                new ClientConferencesInitializer
                        .Builder(user, conferences)
                        .withContentService(contentService)
                        .withRequestService(requestService)
                        .withSectionService(sectionService)
                        .withUserService(userService)
        );
        loader.init();
        content.setRequestAttribute("conferences", conferences);
        JsonArray jsonArray = new JsonArray();
        for(Conference c :conferences){
            JsonConference jsonConference = new ConferenceConverter().convert(c);
            jsonArray.add(new Gson().toJsonTree(jsonConference, JsonConference.class));
        }

//        if (conferences.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
//                conferenceService.getTheOldest().getId().equals(conferences.get(conferences.size() - 1).getId())) {
//            content.setRequestAttribute("hideViewMoreButton", Boolean.TRUE);
//        }
        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.putPOJO("conferences", jsonArray);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }

        return Optional.empty();
    }
}
