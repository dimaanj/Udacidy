package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.Content;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class RemoveConferenceCommand implements Command {
    private final ContentService contentService;
    private final ConferenceService conferenceService;
    private final SectionService sectionService;
    private final RequestService requestService;

    public RemoveConferenceCommand(ServiceFactory serviceFactory){
        contentService = serviceFactory.createContentService();
        conferenceService = serviceFactory.createConferenceService();
        sectionService = serviceFactory.createSectionService();
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        Content conferenceContent = contentService.findByConferenceId(conferenceId);

        boolean didConferenceExist;
        String message = null;
        List<Section> sectionList = sectionService.findSectionsByConferenceId(conferenceId);
        if(!sectionList.isEmpty()) {
            for (Section s : sectionList) {
                List<Request> requests = requestService.findBySectionId(s.getId());
                for(Request r : requests){
                    requestService.delete(r.getId());
                }
                sectionService.deleteSectionWithTheirContent(s.getId(), s.getContentId());
            }
            conferenceService.deleteConferenceWithTheirContent(conferenceId, conferenceContent.getId());
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
