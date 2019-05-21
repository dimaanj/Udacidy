package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;
import java.util.Optional;

public class ProfileCommand implements Command {
    private final ConferenceService conferenceService;
    private final SectionService sectionService;
    private final ContentService contentService;

    public ProfileCommand(ServiceFactory serviceFactory){
        conferenceService = serviceFactory.createConferenceService();
        sectionService = serviceFactory.createSectionService();
        contentService = serviceFactory.createContentService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        List<Conference> conferences = conferenceService.findAllConferencesAsUserRequestsByUserId(user.getId());

        if(!conferences.isEmpty()){
            for(Conference c : conferences){
                List<Section> sections = sectionService.findSectionsByConferenceId(c.getId());
                for(Section s : sections){
                    s.setContent(contentService.findById(s.getContentId()));
                }
                c.setContent(contentService.findById(c.getContentId()));
                c.setSections(sections);
            }
            content.setRequestAttribute("conferences", conferences);
        }else {
            content.setRequestAttribute("noneRequests", "You doesn't have any requests.");
        }

        return Optional.of(JSP.PROFILE);
    }
}
