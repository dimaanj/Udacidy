package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class RemoveConferenceCommand implements Command {
    private static final ContentService CONTENT_SERVICE = new ContentServiceImpl();
    private static final ConferenceService CONFERENCE_SERVICE = new ConferenceServiceImpl();
    private static final SectionService SECTION_SERVICE = new SectionServiceImpl();
    private static final RequestService REQUEST_SERVICE = new RequestServiceImpl();
    private static final RequestDataService REQUEST_FORM_SERVICE = new RequestDataServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        Content conferenceContent = CONTENT_SERVICE.findByConferenceId(conferenceId);

        boolean didConferenceExist;
        String message = null;
        List<Section> sectionList = SECTION_SERVICE.findSectionsByConferenceId(conferenceId);
        if(!sectionList.isEmpty()) {
            for (Section s : sectionList) {
                List<Request> requests = REQUEST_SERVICE.findBySectionId(s.getId());
                for(Request r : requests){
                    REQUEST_SERVICE.deleteRequestWithRequestData(r.getId());
                }
                SECTION_SERVICE.deleteSectionWithTheirContent(s.getId(), s.getContentId());
            }
            CONFERENCE_SERVICE.deleteConferenceWithTheirContent(conferenceId, conferenceContent.getId());
            message = "You successfully deleted this conference with id=" + conferenceId + "!";
            didConferenceExist = true;
        }else {
            message = "You had tried to delete a conference that didn't existed!";
            didConferenceExist = false;
        }

        try {
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put("didConferenceExist", didConferenceExist);
            node.put("message", message);

            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
