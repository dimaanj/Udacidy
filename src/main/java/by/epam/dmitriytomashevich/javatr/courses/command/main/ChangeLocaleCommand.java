package by.epam.dmitriytomashevich.javatr.courses.command.main;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ChangeLocaleCommand implements Command {


    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String newLocale = content.getParameter("locale");
        if(newLocale != null &&
                (newLocale.equalsIgnoreCase("en") || newLocale.equalsIgnoreCase("ru"))){
            content.getSession().removeAttribute("locale");
            content.getSession().setAttribute("locale", newLocale);
        }
        String action = content.getRequest().getRequestURI();
        return Optional.of(action);
    }
}
