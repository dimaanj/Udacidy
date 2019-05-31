package by.epam.dmitriytomashevich.javatr.courses.db;

import by.epam.dmitriytomashevich.javatr.courses.constant.DbInitParameterName;
import by.epam.dmitriytomashevich.javatr.courses.controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class.getName());

    private BlockingQueue<Connection> connectionPool;
    private BlockingQueue<Connection> usedConnections;

    public static ConnectionPool getInstance() {
        return DBConnectionPoolHolder.INSTANCE;
    }

    private static class DBConnectionPoolHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    private ConnectionPool() {
        ResourceBundle bundle = ResourceBundle.getBundle(DbInitParameterName.DB_PROPERTIES_FILE_NAME);
        int poolSize = Integer.parseInt(bundle.getString(DbInitParameterName.DB_POOL_SIZE));
        String driverName = bundle.getString(DbInitParameterName.DB_DRIVER);
        String url = bundle.getString(DbInitParameterName.DB_URL);
        String user = bundle.getString(DbInitParameterName.DB_USER);
        String password = bundle.getString(DbInitParameterName.DB_PASSWORD);

        connectionPool = new ArrayBlockingQueue<>(poolSize);
        usedConnections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = null;
            try {
                connection = createConnection(driverName, url, user, password);
                connectionPool.add(connection);
                LOGGER.info("Connection " + connection + " was created");
            } catch (SQLException e) {
                LOGGER.error("Something with connection pool", e);
            }
        }
    }

    private static Connection createConnection(String driverName, String url, String user, String password)
            throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionPool.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            LOGGER.error("Something with connection pool", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            connection.clearWarnings();
        } catch (SQLException e) {
            LOGGER.error("Something with connection pool", e);
        }
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
            LOGGER.info("Connection "+ c + "was closed");
        }
        connectionPool.clear();
        usedConnections.clear();
    }
}
