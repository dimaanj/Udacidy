package by.epam.dmitriytomashevich.javatr.courses.command.action;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.Optional;

public class CoursesCommand implements Command {
    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        return Optional.empty();
    }
}
