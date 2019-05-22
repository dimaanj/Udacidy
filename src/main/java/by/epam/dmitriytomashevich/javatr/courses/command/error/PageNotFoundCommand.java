package by.epam.dmitriytomashevich.javatr.courses.command.error;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.Optional;

public class PageNotFoundCommand implements Command {
    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        content.setActionType(SessionRequestContent.ActionType.FORWARD);
        return Optional.of(ActionNames.PAGE_404);
    }
}
