package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Conference;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConferenceServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ContentServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.SectionServiceImpl;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class CreateConferenceCommand implements Command {
    private static final ConferenceService CONFERENCE_SERVICE = new ConferenceServiceImpl();
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();
    private static final SectionService SECTION_SERVICE = new SectionServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User admin = (User) content.getSession(false).getAttribute(Parameter.USER);

        String htmlContent = content.getParameter(Parameter.CONTENT);
        List<String> jsonSections = new Gson().fromJson(content.getParameter(Parameter.SECTIONS), List.class);

        Content conferenceContent = new Content();
        conferenceContent.setContent(htmlContent);
        Long conferenceContentId = CONTENT_SERVICE.create(conferenceContent);

        Conference conference = new Conference();
        conference.setContentId(conferenceContentId);
        conference.setAuthorId(admin.getId());
        Long conferenceId = CONFERENCE_SERVICE.create(conference);


        for(String stringSection : jsonSections){
            Content sectionContent = new Content();
            sectionContent.setContent(stringSection);
            Long sectionContentId = CONTENT_SERVICE.create(sectionContent);

            Section section = new Section();
            section.setConferenceId(conferenceId);
            section.setContentId(sectionContentId);

            SECTION_SERVICE.create(section);
        }

        try {
            content.getResponse().setContentType("text/plain");
            PrintWriter writer = content.getResponse().getWriter();
            writer.print("You successfully created new conference! Congratulations!");
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
