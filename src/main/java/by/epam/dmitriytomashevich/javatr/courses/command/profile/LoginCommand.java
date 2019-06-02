package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.MessageNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.UserServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.util.MessageManager;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.UserServiceHandler;
import by.epam.dmitriytomashevich.javatr.courses.util.validation.InputValidator;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private final UserService userService = new UserServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String locale = (String) content.getSession(false).getAttribute(ParameterNames.LOCALE);
        String page;
        if (content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)) {
            page = ActionNames.LOGIN;
        } else {
            String email = content.getParameter(ParameterNames.EMAIL);
            String password = content.getParameter(ParameterNames.PASSWORD);

            boolean isParametersValid = InputValidator.validateUserLoginParameters(email, password);
            if (!isParametersValid) {
                String msg = MessageManager.getMessage(MessageNames.LOGIN_SERVER_SIDE_VALIDATION_FAILS, locale);
                content.setRequestAttribute(ParameterNames.ERROR_LOGIN_PASS_MESSAGE, msg);
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                return Optional.of(ActionNames.LOGIN);
            }

            UserServiceHandler handler = new UserServiceHandler();
            User user = handler.checkLogin(password, userService.findUserByEmail(email));
            if (user != null) {
                HttpSession session = content.getSession();
                session.setAttribute(ParameterNames.USER, user);
                page = user.isAdmin() ? ActionNames.CONTENT_EDITING_ACTION : ActionNames.CONFERENCES_ACTION;
            } else {
                String msg = MessageManager.getMessage(MessageNames.INVALID_CREDENTIALS, locale);
                content.setRequestAttribute(ParameterNames.ERROR_LOGIN_PASS_MESSAGE, msg);
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                page = ActionNames.LOGIN;
            }
        }
        return Optional.of(page);
    }
}


