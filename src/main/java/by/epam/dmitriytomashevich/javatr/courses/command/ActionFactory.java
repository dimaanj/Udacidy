package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.command.error.PageNotFoundCommand;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public Command defineCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();

        String action = null;
        if(request.getMethod().equals(Parameter.METHOD_GET)){
             action = request.getRequestURI();
        }else if(request.getMethod().equals(Parameter.METHOD_POST)){
             action = request.getParameter(Parameter.COMMAND);
            if (action == null || action.isEmpty()) {
                return current;
            }
        }

        try {
            CommandEnum currentEnum = CommandEnum.fromValue(action);
            current = currentEnum.getCommand();
        } catch (IllegalArgumentException e) {
            current = new PageNotFoundCommand();
        }
        return current;
    }
}
