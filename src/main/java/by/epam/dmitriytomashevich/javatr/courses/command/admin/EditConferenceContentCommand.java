package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class EditConferenceContentCommand implements Command {
    private final ContentService contentService;

    public EditConferenceContentCommand(ServiceFactory serviceFactory){
        contentService = serviceFactory.createContentService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        String htmlConferenceContent = content.getParameter("content");

        String message = null;
        boolean isConferenceExists;
        Content conferenceContent = contentService.findByConferenceId(conferenceId);
        if(conferenceContent != null) {
            conferenceContent.setContent(htmlConferenceContent);
            contentService.update(conferenceContent);
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
