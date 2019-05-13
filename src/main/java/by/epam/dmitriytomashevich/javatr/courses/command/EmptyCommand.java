package by.epam.dmitriytomashevich.javatr.courses.command;

import java.util.Optional;

public class EmptyCommand implements Command {
    @Override
    public Optional<String> execute(SessionRequestContent content) {
        return Optional.empty();
    }
}
