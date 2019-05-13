package by.epam.dmitriytomashevich.javatr.courses.logic.builder;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilder implements EntityBuilder<User> {
    @Override
    public User build(ResultSet resultSet) throws DAOException {
        User user = new User();
        try {
            user.setId(resultSet.getLong("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setUserRole(UserRole.valueOf(resultSet.getString("role")));
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }
}
