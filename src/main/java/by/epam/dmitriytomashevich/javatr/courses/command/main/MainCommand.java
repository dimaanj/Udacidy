package by.epam.dmitriytomashevich.javatr.courses.command.main;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonSection;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainCommand implements Command {
    private static final ConferenceService CONFERENCE_SERVICE = new ConferenceServiceImpl();
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final SectionService SECTION_SERVICE = new SectionServiceImpl();
    private static final RequestService REQUEST_SERVICE = new RequestServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {

        List<Conference> conferenceList = CONFERENCE_SERVICE.findSomeLastConferences(Parameter.CONFERENCES_UPDATE_AMOUNT);
        JsonArray jsonConferencesList = new JsonArray();
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        for (Conference c : conferenceList) {
            Gson gson = new Gson();

            User author = USER_SERVICE.findById(c.getAuthorId());
            List<Section> sections = SECTION_SERVICE.findSectionsByConferenceId(c.getId());
            List<JsonSection> jsonSections = new ArrayList<>();

            for(Section s : sections){
                JsonSection jsonSection = new JsonSection();
                Content sectionContent = CONTENT_SERVICE.findById(s.getContentId());
                jsonSection.setId(s.getId());
                jsonSection.setContentId(s.getContentId());
                jsonSection.setConferenceId(s.getConferenceId());
                jsonSection.setContent(sectionContent.getContent());
                jsonSections.add(jsonSection);

            }

            JsonConference conference = new JsonConference();
            conference.setId(c.getId());
            conference.setAuthorId(author.getId());
            conference.setContent(CONTENT_SERVICE.findById(c.getContentId()).getContent());
            conference.setContentId(c.getContentId());
            conference.setAuthorFirstName(author.getFirstName());
            conference.setAuthorLastName(author.getLastName());
            conference.setJsonSection(jsonSections);

            if(!user.isAdmin()){
                conference.setRequestAlreadySent(
                        isRequestAlreadySentRequest(user, sections));
            }

            JsonElement conferenceJsonElement = gson.toJsonTree(conference, JsonConference.class);
            jsonConferencesList.add(conferenceJsonElement);
        }

        content.setRequestAttribute("conferences", jsonConferencesList);

        if(jsonConferencesList.size() < Parameter.CONFERENCES_UPDATE_AMOUNT ||
                CONFERENCE_SERVICE.getTheOldest().getId().equals(conferenceList.get(conferenceList.size()-1).getId())){
            content.setRequestAttribute("hideViewMoreButton",  Boolean.TRUE);
        }

        return Optional.of(JSP.MAIN);
    }

    private boolean isRequestAlreadySentRequest(User user, List<Section> sections) throws LogicException {
        for(Section s : sections){
            if (REQUEST_SERVICE.findBySectionIdAndUserId(s.getId(), user.getId()) != null){
                return true;
            }
        }
        return false;
    }
}
