package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.Optional;

public interface Command {
    Optional<String> execute(SessionRequestContent content) throws LogicException;
}
