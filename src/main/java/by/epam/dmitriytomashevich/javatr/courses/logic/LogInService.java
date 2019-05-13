package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

public interface LogInService {
    User checkLogin(String email, String password) throws LogicException;
    String encodePassword(String password);
}
