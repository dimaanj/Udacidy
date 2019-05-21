package by.epam.dmitriytomashevich.javatr.courses.command.admin;

import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.Optional;

public class PreviewCourseContent implements Command {
    @Override
    public Optional<String> execute(SessionRequestContent content) throws LogicException {
        return Optional.empty();
    }
}
