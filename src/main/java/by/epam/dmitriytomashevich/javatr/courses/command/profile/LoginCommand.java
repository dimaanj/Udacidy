package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.UserServiceHandler;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(ServiceFactory serviceFactory){
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String page;
        if(content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)){
            page = JSP.LOGIN;
        }else {
            String email = content.getParameter("email");
            String password = content.getParameter("password");

            User user = userService.findUserByEmail(email);
            UserServiceHandler handler = new UserServiceHandler();
            user = handler.checkLogin(email, password, user);

            if (user != null) {
                HttpSession session = content.getSession();
                session.setAttribute(Parameter.USER, user);
                page = JSP.MAIN_ACTION;
            } else {
                content.setRequestAttribute("errorLoginPassMessage", "Invalid credentials!");
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                page = JSP.LOGIN;
            }
        }
        return Optional.of(page);
    }
}
