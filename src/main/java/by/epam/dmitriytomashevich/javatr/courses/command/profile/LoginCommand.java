package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.LogInService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.LogInServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final LogInService LOG_IN_LOGIC = new LogInServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String page;
        if(content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)){
            page = JSP.LOGIN;
        }else {
            String email = content.getParameter("email");
            String password = content.getParameter("password");
            User user = LOG_IN_LOGIC.checkLogin(email, password);

            if (user != null) {
                HttpSession session = content.getSession();
                session.setAttribute(Parameter.USER, user);
                if (user.isAlreadyLoggedIn()) {
                    session.removeAttribute(Parameter.USER);
                    content.setRequestAttribute("errorLoginPassMessage", "User has already logged in!");
                    content.setActionType(SessionRequestContent.ActionType.FORWARD);
                    page = JSP.LOGIN;
                } else {
                    session.setAttribute(Parameter.USER, user);
                    page = JSP.GREETING_ACTION;
                }
            } else {
                content.setRequestAttribute("errorLoginPassMessage", "Invalid credentials!");
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                page = JSP.LOGIN;
            }
        }
        return Optional.of(page);
    }
}
