package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ContentServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class EditConferenceContentCommand implements Command {
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        String htmlConferenceContent = content.getParameter("content");

        Content conferenceContent = CONTENT_SERVICE.findByConferenceId(conferenceId);
        conferenceContent.setContent(htmlConferenceContent);
        CONTENT_SERVICE.update(conferenceContent);

        try {
            content.getResponse().setContentType("text/plain");
            PrintWriter writer = content.getResponse().getWriter();
            writer.print("You successfully updated content of this conference!");
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
