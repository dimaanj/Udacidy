package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.logic.ConferenceService;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ConferenceServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ContentServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.SectionServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class RemoveConferenceCommand implements Command {
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();
    private static final ConferenceService CONFERENCE_SERVICE = new ConferenceServiceImpl();
    private static final SectionService SECTION_SERVICE = new SectionServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        Content conferenceContent = CONTENT_SERVICE.findByConferenceId(conferenceId);

        List<Section> sectionList = SECTION_SERVICE.findByConferenceId(conferenceId);
        for(Section s : sectionList){
            SECTION_SERVICE.delete(s.getId());
            CONTENT_SERVICE.delete(s.getContentId());
        }

        CONFERENCE_SERVICE.delete(conferenceId);
        CONTENT_SERVICE.delete(conferenceContent.getId());

        try {
            content.getResponse().setContentType("text/plain");
            PrintWriter writer = content.getResponse().getWriter();
            writer.print("You successfully deleted this conference with id=" + conferenceId + "!");
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
