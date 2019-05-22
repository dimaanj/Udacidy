package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.util.UserServiceHandler;

import java.util.Optional;

public class RegistrationCommand implements Command {
    private final UserService userService;

    public RegistrationCommand(ServiceFactory serviceFactory){
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String action = null;
        if(content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)){
            action = ActionNames.REGISTRATION;
        }else{
            if(userService.addUser(defineUserData(content))){
                action = ActionNames.LOGIN_ACTION;
            }else {
                content.setRequestAttribute("errorRegistrationPassMessage", "User already exists!");
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                action = ActionNames.REGISTRATION;
            }
        }
        return Optional.of(action);
    }

    private User defineUserData(SessionRequestContent content){
        User user = new User();
        user.setFirstName(content.getParameter(Parameter.FIRST_NAME));
        user.setLastName(content.getParameter(Parameter.LAST_NAME));
        user.setEmail(content.getParameter(Parameter.EMAIL));
        user.setPassword(new UserServiceHandler().encodePassword(content.getParameter(Parameter.PASSWORD)));
        user.setUserRole(UserRole.USER);
        return user;
    }
}
