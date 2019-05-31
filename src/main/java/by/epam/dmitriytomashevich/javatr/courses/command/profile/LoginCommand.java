package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.MessageNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.MessageManager;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.UserServiceHandler;

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
            page = ActionNames.LOGIN;
        }else {
            String email = content.getParameter(ParameterNames.EMAIL);
            String password = content.getParameter(ParameterNames.PASSWORD);

            User user = userService.findUserByEmail(email);
            UserServiceHandler handler = new UserServiceHandler();
            user = handler.checkLogin(password, user);

            if (user != null) {
                HttpSession session = content.getSession();
                session.setAttribute(ParameterNames.USER, user);
                if(user.isAdmin()){
                    page = ActionNames.CONTENT_EDITING_ACTION;
                }else {
                    page = ActionNames.CONFERENCES_ACTION;
                }
            } else {
                String locale = (String) content.getSession(false).getAttribute(ParameterNames.LOCALE);
                String msg = MessageManager.getMessage(MessageNames.INVALID_CREDENTIALS, locale);
                content.setRequestAttribute(ParameterNames.ERROR_LOGIN_PASS_MESSAGE, msg);
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                page = ActionNames.LOGIN;
            }
        }
        return Optional.of(page);
    }
}


