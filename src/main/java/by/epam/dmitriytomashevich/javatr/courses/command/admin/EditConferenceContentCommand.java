package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ContentServiceImpl;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class EditConferenceContentCommand implements Command {
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        String htmlConferenceContent = content.getParameter("content");

        String message = null;
        boolean isConferenceExists;
        Content conferenceContent = CONTENT_SERVICE.findByConferenceId(conferenceId);
        if(conferenceContent != null) {
            conferenceContent.setContent(htmlConferenceContent);
            CONTENT_SERVICE.update(conferenceContent);
            message = "You successfully updated content of this conference!";
            isConferenceExists = true;
        }else {
            message = "Sorry, this conference already deleted! Please try to refresh the page!";
            isConferenceExists = false;
        }
        try {
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put("isConferenceExists", isConferenceExists);
            node.put("message", message);

            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
