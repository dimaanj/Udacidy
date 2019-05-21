package by.epam.dmitriytomashevich.javatr.courses.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public class UserServiceHandler {
    public User checkLogin(String email, String password, User user) throws LogicException {
        if (user != null && BCrypt.verifyer()
                .verify(password.toCharArray(), user.getPassword())
                .verified) {
            return user;
        }
        return null;
    }

    public String encodePassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
