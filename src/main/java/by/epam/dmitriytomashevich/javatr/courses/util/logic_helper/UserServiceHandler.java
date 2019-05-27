package by.epam.dmitriytomashevich.javatr.courses.util.logic_helper;

import at.favre.lib.crypto.bcrypt.BCrypt;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

public class UserServiceHandler {
    public User checkLogin(String password, User user) throws LogicException {
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

    public boolean isPreviousPasswordCorrect(String previousPassword, User user){
        return BCrypt.verifyer()
                .verify(previousPassword.toCharArray(), user.getPassword())
                .verified;
    }
}
