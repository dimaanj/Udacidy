package by.epam.dmitriytomashevich.javatr.courses.db;

import by.epam.dmitriytomashevich.javatr.courses.constant.DbInitParameterName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private int poolSize;
    private String driverName;
    private String url;
    private String user;
    private String password;

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
        this.poolSize = Integer.parseInt(bundle.getString(DbInitParameterName.DB_POOL_SIZE));
        this.driverName = bundle.getString(DbInitParameterName.DB_DRIVER);
        this.url = bundle.getString(DbInitParameterName.DB_URL);
        this.user = bundle.getString(DbInitParameterName.DB_USER);
        this.password = bundle.getString(DbInitParameterName.DB_PASSWORD);

        connectionPool = new ArrayBlockingQueue<>(poolSize);
        usedConnections = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Connection connection = null;
            try {
                connection = createConnection(driverName, url, user, password);
                connectionPool.add(connection);
                System.out.println("connection " + connection + " was created");
            } catch (SQLException e) {
                e.printStackTrace();
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
        Connection connection = connectionPool.poll();
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        try {
            connection.clearWarnings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public void shutdown() throws SQLException {
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
        usedConnections.clear();
    }
}
