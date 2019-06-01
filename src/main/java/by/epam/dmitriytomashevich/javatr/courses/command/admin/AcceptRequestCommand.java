package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestStatus;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.RequestServiceImpl;

import java.util.Optional;

public class AcceptRequestCommand implements Command {

    private final RequestService requestService = new RequestServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long userId = Long.valueOf(content.getParameter("userId"));
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));

        requestService.updateRequestStatusByUserIdAndConferenceId(userId, conferenceId, RequestStatus.ACCEPTED);
        return Optional.empty();
    }
}
