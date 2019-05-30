package by.epam.dmitriytomashevich.javatr.courses.listener;

import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.getInstance().shutdown();
        } catch (SQLException e) {
            //  todo logger
            e.printStackTrace();
        }
    }
}
