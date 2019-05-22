package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.json.JsonConference;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.util.converter.ConferenceConverter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.List;
import java.util.Optional;

public class ProfileCommand implements Command {
    private final ConferenceService conferenceService;
    private final SectionService sectionService;
    private final ContentService contentService;
    private final UserService userService;

    public ProfileCommand(ServiceFactory serviceFactory) {
        conferenceService = serviceFactory.createConferenceService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<Conference> conferences = conferenceService.findAllConferencesAsUserRequestsByUserId(user.getId());

        if (!conferences.isEmpty()) {
            for (Conference c : conferences) {
                List<Section> sections = sectionService.findSectionsByConferenceId(c.getId());
                for (Section s : sections) {
                    s.setContent(contentService.findById(s.getContentId()));
                }
                c.setContent(contentService.findById(c.getContentId()));
                c.setSections(sections);
                c.setAuthor(userService.findById(c.getAuthorId()));
            }
//            List<JsonConference> jsonConference = new ArrayList<>();
            JsonArray jsonConferenceList = new JsonArray();
            for (Conference c : conferences) {
                jsonConferenceList.add(
                        new Gson()
                                .toJsonTree(new ConferenceConverter().convert(c),
                                        JsonConference.class
                                ));
            }
            content.setRequestAttribute("conferences", jsonConferenceList);
        } else {
            content.setRequestAttribute("noneRequests", "You doesn't have any requests.");
        }

        return Optional.of(ActionNames.PROFILE);
    }
}
