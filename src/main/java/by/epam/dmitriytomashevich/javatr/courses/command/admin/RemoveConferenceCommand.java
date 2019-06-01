package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.*;
import by.epam.dmitriytomashevich.javatr.courses.logic.*;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class RemoveConferenceCommand implements Command {
    private final ContentService contentService = new ContentServiceImpl();
    private final ConferenceService conferenceService = new ConferenceServiceImpl();
    private final SectionService sectionService = new SectionServiceImpl();
    private final RequestService requestService = new RequestServiceImpl();
    private final RequestFormService requestFormService = new RequestFormServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long conferenceId = Long.valueOf(content.getParameter(ParameterNames.CONFERENCE_ID));

        boolean didConferenceExist;
        List<Section> sectionList = sectionService.findSectionsByConferenceId(conferenceId);
        if(!sectionList.isEmpty()) {
            List<Request> requests = requestService.findByConferenceId(conferenceId);
            for(Request r:requests){
                List<RequestForm> requestForms = requestFormService.findByRequestId(r.getId());
                for(RequestForm rf:requestForms){
                    requestFormService.delete(rf.getId());
                }
                requestService.delete(r.getId());
            }
            for (Section s : sectionList) {
                sectionService.deleteSectionWithTheirContent(s.getId(), s.getContentId());
            }
            Content conferenceContent = contentService.findByConferenceId(conferenceId);
            conferenceService.deleteConferenceWithTheirContent(conferenceId, conferenceContent.getId());

            didConferenceExist = true;
        }else {
            didConferenceExist = false;
        }

        try {
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put(ParameterNames.DID_CONFERENCE_EXIST, didConferenceExist);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
