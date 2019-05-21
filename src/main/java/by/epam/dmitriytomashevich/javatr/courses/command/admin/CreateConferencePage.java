package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.Optional;

public class CreateConferencePage implements Command {
    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        if(content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)){
            return Optional.of(JSP.ADD_NEW_COURSE);
        }
        return Optional.empty();
    }
}
