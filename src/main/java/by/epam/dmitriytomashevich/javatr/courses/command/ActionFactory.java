package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.command.error.PageNotFoundCommand;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ActionFactory {
    public Command defineCommand(HttpServletRequest request, CommandFactory commandFactory) {
        String action = null;
        if (request.getMethod().equals(Parameter.METHOD_GET)) {
            action = request.getRequestURI();
        } else if (request.getMethod().equals(Parameter.METHOD_POST)) {
            action = request.getParameter(Parameter.COMMAND);
            if (action == null || action.isEmpty()) {
                return new PageNotFoundCommand();
            }
        }
        Optional<Command> command = commandFactory.createCommand(action);
        return command.orElseGet(PageNotFoundCommand::new);
    }
}
