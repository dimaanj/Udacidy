package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.Optional;

public class LogoutCommand implements Command {

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String page;
        content.getSession().removeAttribute(Parameter.USER); //this will invoke valueUnbound method of UserDto
        content.invalidateSession();
        page = JSP.LOGIN_ACTION;
        return Optional.of(page);
    }
}
