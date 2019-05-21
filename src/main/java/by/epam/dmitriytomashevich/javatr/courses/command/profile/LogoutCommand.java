package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.Optional;

public class LogoutCommand implements Command {

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String page;
        content.getSession().removeAttribute(Parameter.USER);
        content.invalidateSession();
        page = JSP.LOGIN_ACTION;
        return Optional.of(page);
    }
}
