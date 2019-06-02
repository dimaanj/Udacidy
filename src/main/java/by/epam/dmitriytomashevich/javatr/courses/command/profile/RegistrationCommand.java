package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.MessageNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.logic.impl.UserServiceImpl;
import by.epam.dmitriytomashevich.javatr.courses.util.MessageManager;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.UserServiceHandler;
import by.epam.dmitriytomashevich.javatr.courses.util.validation.InputValidator;

import java.util.Optional;

public class RegistrationCommand implements Command {
    private final UserService userService = new UserServiceImpl();

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        String locale = (String) content.getSession(false).getAttribute(ParameterNames.LOCALE);
        String action;
        if(content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)){
            action = ActionNames.REGISTRATION;
        }else{
            boolean isValidUserData = InputValidator.validateUserRegistrationParameters(
                    content.getParameter(ParameterNames.FIRST_NAME),
                    content.getParameter(ParameterNames.LAST_NAME),
                    content.getParameter(ParameterNames.EMAIL),
                    content.getParameter(ParameterNames.PASSWORD),
                    content.getParameter(ParameterNames.CONFIRMED_PASSWORD));

            if(!isValidUserData){
                String msg = MessageManager.getMessage(MessageNames.REGISTRATION_SERVER_SIDE_VALIDATION_FAILS, locale);
                content.setRequestAttribute(ParameterNames.ERROR_REGISTRATION_PASS_MESSAGE, msg);
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                action = ActionNames.REGISTRATION;
            }else if(userService.addUser(defineUserData(content))){
                action = ActionNames.LOGIN_ACTION;
            }else {
                String msg = MessageManager.getMessage(MessageNames.USER_ALREADY_EXISTS, locale);
                content.setRequestAttribute(ParameterNames.ERROR_REGISTRATION_PASS_MESSAGE, msg);
                content.setActionType(SessionRequestContent.ActionType.FORWARD);
                action = ActionNames.REGISTRATION;
            }
        }
        return Optional.of(action);
    }

    private User defineUserData(SessionRequestContent content){
        User user = new User();
        user.setFirstName(content.getParameter(ParameterNames.FIRST_NAME));
        user.setLastName(content.getParameter(ParameterNames.LAST_NAME));
        user.setEmail(content.getParameter(ParameterNames.EMAIL));
        user.setPassword(new UserServiceHandler().encodePassword(content.getParameter(ParameterNames.PASSWORD)));
        user.setUserRole(UserRole.USER);
        return user;
    }
}
