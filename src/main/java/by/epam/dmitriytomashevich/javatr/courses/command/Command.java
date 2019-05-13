package by.epam.dmitriytomashevich.javatr.courses.command;

import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;
import javafx.fxml.LoadException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface Command {
    Optional<String> execute(SessionRequestContent content) throws LogicException;
}
