package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.sql.ResultSet;
import java.util.List;

public interface UserService {
    boolean addUser(User user) throws LogicException;
    User findUserByEmail(String email) throws LogicException;
    List<User> findAllByConversationId(Long id) throws LogicException;
    void updateUserActive(long id, boolean active);
    User findById(Long id) throws LogicException;
}
