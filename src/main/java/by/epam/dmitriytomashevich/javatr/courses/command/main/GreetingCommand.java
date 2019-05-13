package by.epam.dmitriytomashevich.javatr.courses.command.main;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.Optional;

public class GreetingCommand implements Command {
    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        return Optional.of(JSP.GREETING);
    }
}
