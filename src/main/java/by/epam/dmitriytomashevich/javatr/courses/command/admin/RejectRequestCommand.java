package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.domain.RequestStatus;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.RequestService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class RejectRequestCommand implements Command {
    private final RequestService requestService;

    public RejectRequestCommand(ServiceFactory serviceFactory){
        requestService = serviceFactory.createRequestService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        Long userId = Long.valueOf(content.getParameter("userId"));
        Long conferenceId = Long.valueOf(content.getParameter("conferenceId"));
        requestService.updateRequestStatusByUserIdAndConferenceId(userId, conferenceId, RequestStatus.REJECTED);
        return Optional.empty();
    }
}
