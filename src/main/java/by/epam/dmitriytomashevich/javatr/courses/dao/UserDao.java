package by.epam.dmitriytomashevich.javatr.courses.dao;

import by.epam.dmitriytomashevich.javatr.courses.dao.exception.DAOException;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.logic.builder.UserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements AbstractDao<Long, User> {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private EntityBuilder<User> builder = new UserBuilder();

    private static final String SELECT_ALL_BY_CONVERSATION_ID  = "SELECT u.id, u.first_name, u.last_name, u.password, u.email, ur.role\n" +
            "FROM conversation c\n" +
            "        JOIN conversation_group cg on c.id = cg.conversation_id\n" +
            "        JOIN user u on cg.user_id = u.id\n" +
            "JOIN user_role ur on u.id = ur.id\n" +
            "        WHERE c.id = ?";

    private static final String SELECT_BY_EMAIL = "SELECT u.id, u.first_name, u.last_name, u.password, u.email, r.role\n" +
            "\tFROM udacidy.user u\n" +
            "    JOIN user_role r\n" +
            "\t\tON r.id = u.id\n" +
            "    WHERE u.email = ?";

    private static final String SELECT_BY_ID = "SELECT u.id, u.first_name, u.email, u.last_name, u.password, ur.role\n" +
            "FROM udacidy.user u\n" +
            "         JOIN user_role ur\n" +
            "             on u.id = ur.id\n" +
            "WHERE u.id = ?\n";

    private static final String SELECT_BY_ROLE = "SELECT u.id, u.first_name, u.last_name, u.password, u.email\n" +
            "FROM udacidy.user u\n" +
            "  JOIN user_role ur\n" +
            "    ON u.id = ur.id\n" +
            "  WHERE ur.role = ?";

    private static final String INSERT_USER =
            "\tINSERT INTO udacidy.user (first_name, last_name, password, email)\n" +
            "\t\tVALUES (?, ?, ?, ?);\n";
    private static final String INSERT_USER_ROLE = "INSERT INTO udacidy.user_role (id, role)\n" +
            "VALUES(LAST_INSERT_ID(), ?)";

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) throws DAOException {
        User user = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = builder.build(resultSet);
            }
            resultSet.close();
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Long create(User entity) throws DAOException {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getEmail());
            statement.execute();
            statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setString(1, entity.getRole().getValue().toUpperCase());
            statement.execute();
            connection.commit();
            return entity.getId();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void update(User entity) {

    }

    public User findByEmail(String email) throws DAOException {
        User user = null;
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = builder.build(resultSet);
            }
            resultSet.close();
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private List<User> findByUserRole(UserRole role) throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ROLE);
            statement.setString(1, role.getValue().toUpperCase());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = builder.build(resultSet);
                users.add(user);
            }
            resultSet.close();
            return users;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    public List<User> findAllByConversationId(Long id) throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_CONVERSATION_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = builder.build(resultSet);
                users.add(user);
            }
            resultSet.close();
            return users;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
