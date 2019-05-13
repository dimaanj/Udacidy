package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.LogInService;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;


public class LogInServiceImpl implements LogInService {
    private static final UserService USER_LOGIC = new UserServiceImpl();

    @Override
    public User checkLogin(String email, String password) throws LogicException {
        User user = USER_LOGIC.findUserByEmail(email);
        if (user != null && BCrypt.verifyer()
                .verify(password.toCharArray(), user.getPassword())
                .verified) {
            return user;
        }
        return null;
    }

    @Override
    public String encodePassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
