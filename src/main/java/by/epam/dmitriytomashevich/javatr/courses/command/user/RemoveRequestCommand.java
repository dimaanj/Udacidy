package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.Request;
import by.epam.dmitriytomashevich.javatr.courses.domain.Section;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestFormService;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.SectionService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestFormServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.SectionServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public class RemoveRequestCommand implements Command {
    private static final RequestService REQUEST_SERVICE = new RequestServiceImpl();
    private static final RequestFormService REQUEST_FORM_SERVICE = new RequestFormServiceImpl();
    private static final SectionService SECTION_SERVICE = new SectionServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);

        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        List<Section> sectionList = SECTION_SERVICE.findSectionsByConferenceId(conferenceId);

        for(Section s :sectionList){
            Request request = null;
            if((request = REQUEST_SERVICE.findBySectionIdAndUserId(s.getId(), user.getId())) != null){
                REQUEST_FORM_SERVICE.deleteByRequestId(request.getId());
                REQUEST_SERVICE.delete(request.getId());
                break;
            }
        }

        try {
            content.getResponse().setContentType("text/plain");
            PrintWriter writer = content.getResponse().getWriter();
            writer.print("Your request was removed successfully!");
        } catch (IOException e) {
            throw new LogicException(e);
        }
        return Optional.empty();
    }
}
