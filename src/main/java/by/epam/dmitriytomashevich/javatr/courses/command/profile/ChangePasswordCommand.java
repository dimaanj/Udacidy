package by.epam.dmitriytomashevich.javatr.courses.command.profile;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.util.logic_helper.UserServiceHandler;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

public class ChangePasswordCommand implements Command {
    private final UserService userService;

    public ChangePasswordCommand(ServiceFactory serviceFactory){
        userService = serviceFactory.createUserService();
    }

    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        User user = (User) content.getSession(false).getAttribute(ParameterNames.USER);
        String previousPassword = content.getParameter("previousPassword");
        String newPassword = content.getParameter("newPassword");

        User current = userService.findById(user.getId());
        UserServiceHandler handler = new UserServiceHandler();
        boolean isPreviousPasswordCorrect =
                handler.isPreviousPasswordCorrect(previousPassword, current);
        String message = null;
        if(isPreviousPasswordCorrect){
             newPassword = handler.encodePassword(newPassword);
             userService.updatePassword(newPassword, user.getId());
             message = "You successfully updated password!";
        }else {
             message = "Previous password is incorrect";
        }

        try {
            content.getResponse().setContentType("application/json;charset=UTF-8");
            final JsonNodeFactory factory = JsonNodeFactory.instance;
            final ObjectNode node = factory.objectNode();
            node.put("isPreviousPasswordCorrect", isPreviousPasswordCorrect);
            node.put("message", message);
            PrintWriter writer = content.getResponse().getWriter();
            writer.print(node);
        } catch (IOException e) {
            throw new LogicException(e);
        }

        return Optional.empty();
    }
}
