package by.epam.dmitriytomashevich.javatr.courses.command.user;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;

import java.util.Optional;

public class RemoveRequestCommand implements Command {
    private final RequestService requestService;

    public RemoveRequestCommand(ServiceFactory serviceFactory) {
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(Parameter.USER);
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        requestService.deleteFullRequestByConferenceIdAndUserId(conferenceId, user.getId());

        String removeRequestMessage = "Your request was successfully removed!";
        return Optional.of(ActionNames.PROFILE_ACTION + "?removeRequestMessage=" + removeRequestMessage);

    }
}
