package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.logic.ContentService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.ContentServiceImpl;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class EditConferenceContentCommand implements Command {
    private final ContentService contentService = new ContentServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter(ParameterNames.CONFERENCE_ID));
        String htmlConferenceContent = content.getParameter(ParameterNames.CONTENT);

        boolean isConferenceExists;
        Content conferenceContent = contentService.findByConferenceId(conferenceId);
        if(conferenceContent != null) {
            conferenceContent.setContent(htmlConferenceContent);
            contentService.update(conferenceContent);
            isConferenceExists = true;
        }else {
            isConferenceExists = false;
        }
        try {
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put(ParameterNames.IS_CONFERENCE_EXISTS, isConferenceExists);

            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
