package by.epam.dmitriytomashevich.javatr.courses.logic;

import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;

import java.util.List;

public interface UserService {
    boolean addUser(User user) throws LogicException;
    User findUserByEmail(String email) throws LogicException;
    List<User> findAllByConversationId(Long id) throws LogicException;
    User findById(Long id) throws LogicException;
}
