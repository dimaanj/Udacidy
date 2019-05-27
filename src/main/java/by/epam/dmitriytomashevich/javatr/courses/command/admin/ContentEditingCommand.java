package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.ConferenceConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Optional;

public class ContentEditingCommand implements Command {
    private final ConferenceService conferenceService;
    private final UserService userService;
    private final SectionService sectionService;
    private final ContentService contentService;

    public ContentEditingCommand(ServiceFactory serviceFactory){
        conferenceService = serviceFactory.createConferenceService();
        userService = serviceFactory.createUserService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        List<Conference> conferenceList = conferenceService.findSomeLastConferences(Parameter.CONFERENCES_UPDATE_AMOUNT);
        JsonArray jsonConferencesList = new JsonArray();
        for (Conference c : conferenceList) {
            User author = userService.findById(c.getAuthorId());
            List<Section> sections = sectionService.findSectionsByConferenceId(c.getId());
            for(Section s : sections){
                s.setContent(contentService.findById(s.getContentId()));
            }
            c.setContent(contentService.findById(c.getContentId()));
            c.setAuthor(author);
            c.setSections(sections);

            JsonConference jsonConference = new ConferenceConverter().convert(c);
            JsonElement conferenceJsonElement = new Gson().toJsonTree(jsonConference, JsonConference.class);
            jsonConferencesList.add(conferenceJsonElement);
        }
        content.setRequestAttribute("conferences", jsonConferencesList);

        if(jsonConferencesList.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
                conferenceService.getTheOldest().getId().equals(conferenceList.get(conferenceList.size()-1).getId())){
            content.setRequestAttribute("hideViewMoreButton",  Boolean.TRUE);
        }
        return Optional.of(ActionNames.CONTENT_EDITING);
    }
}
