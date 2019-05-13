package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.logic.LogInService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.LogInServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.UserServiceImpl;

import java.util.Optional;

public class RegistrationCommand implements Command {
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final LogInService LOG_IN_SERVICE = new LogInServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String action = null;
        if(content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)){
            action = JSP.REGISTRATION;
        }else{
            if(USER_SERVICE.addUser(defineUserData(content))){
                action = JSP.LOGIN_ACTION;
            }else {
                content.setRequestAttribute("errorRegistrationPassMessage", "User already exists!");
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                action = JSP.REGISTRATION;
            }
        }
        return Optional.of(action);
    }

    private User defineUserData(SessionRequestContent content){
        User user = new User();
        user.setFirstName(content.getParameter(Parameter.FIRST_NAME));
        user.setLastName(content.getParameter(Parameter.LAST_NAME));
        user.setEmail(content.getParameter(Parameter.EMAIL));
        user.setPassword(LOG_IN_SERVICE.encodePassword(content.getParameter(Parameter.PASSWORD)));
        user.setUserRole(UserRole.USER);
        return user;
    }
}
