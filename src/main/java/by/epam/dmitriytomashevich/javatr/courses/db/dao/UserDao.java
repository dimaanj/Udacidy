package by.epam.dmitriytomashevich.javatr.courses.db.dao;

import by.epam.dmitriytomashevich.javatr.courses.db.builder.EntityBuilder;
import by.epam.dmitriytomashevich.javatr.courses.db.builder.UserBuilder;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.domain.UserRole;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements AbstractDao<Long, User> {
    private EntityBuilder<User> builder = new UserBuilder();
    private final Connection connection;

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

    private static final String UPDATE_PASSWORD = "UPDATE udacidy.user u\n" +
            "    SET u.password = ?\n" +
            "WHERE u.id = ?";

    public UserDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) throws DAOException {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public Long create(User entity) throws DAOException {
        Long userId = null;
        try {

            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getEmail());
            statement.execute();

            statement = connection.prepareStatement(INSERT_USER_ROLE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getRole().getValue().toUpperCase());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating conversation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating conversation failed, no ID obtained.");
                }
            }

            statement.close();
            return userId;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User entity) {

    }

    public User findByEmail(String email) throws DAOException {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = builder.build(resultSet);
            }
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private List<User> findByUserRole(UserRole role) throws DAOException {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_BY_ROLE);
            statement.setString(1, role.getValue().toUpperCase());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = builder.build(resultSet);
                users.add(user);
            }
            resultSet.close();
            statement.close();
            return users;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<User> findAllByConversationId(Long id) throws DAOException {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_CONVERSATION_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = builder.build(resultSet);
                users.add(user);
            }
            resultSet.close();
            statement.close();
            return users;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void updatePassword(String password, Long userId) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PASSWORD);
            statement.setString(1, password);
            statement.setLong(2, userId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
