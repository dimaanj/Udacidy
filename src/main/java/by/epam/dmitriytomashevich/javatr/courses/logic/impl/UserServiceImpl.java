package by.epam.dmitriytomashevich.javatr.courses.logic.impl;

import by.epam.dmitriytomashevich.javatr.courses.dao.UserDao;
import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.logic.UserService;
import by.epam.dmitriytomashevich.javatr.courses.logic.exception.LogicException;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserDao USER_DAO = new UserDao();

    @Override
    public boolean addUser(User user) throws LogicException {
        try {
            if(USER_DAO.findByEmail(user.getEmail()) == null){
                USER_DAO.create(user);
                return true;
            }else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public User findUserByEmail(String email) throws LogicException {
        try {
            return USER_DAO.findByEmail(email);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public List<User> findAllByConversationId(Long id) throws LogicException {
        try {
            return USER_DAO.findAllByConversationId(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    @Override
    public void updateUserActive(long id, boolean active) {
       // USER_DAO.updateUserActive(id, active);
    }

    @Override
    public User findById(Long id) throws LogicException {
        try {
            return USER_DAO.findById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }


}
