package by.epam.dmitriytomashevich.javatr.courses.controller;

import by.epam.dmitriytomashevich.javatr.courses.command.ActionFactory;
import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.CommandFactory;
import by.epam.dmitriytomashevich.javatr.courses.command.SessionRequestContent;
import by.epam.dmitriytomashevich.javatr.courses.db.ConnectionPool;
import by.epam.dmitriytomashevich.javatr.courses.exceptions.LogicException;
import by.epam.dmitriytomashevich.javatr.courses.factory.DaoFactory;
import by.epam.dmitriytomashevich.javatr.courses.factory.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionRequestContent content = new SessionRequestContent(request, response);
        Optional<String> action = Optional.empty();

        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        CommandFactory commandFactory = createCommandFactory(connection);
        Command command = new ActionFactory().defineCommand(request, commandFactory);

        try {
            action = command.execute(content);
            Connection connection1 = null;
            connectionPool.releaseConnection(connection1);
        } catch (LogicException e) {
            LOGGER.error("Error with controller", e);
        }  finally {
            connectionPool.releaseConnection(connection);
        }
        content.insertAttributes();

        if (action.isPresent()) {
            if (content.getActionType().equals(SessionRequestContent.ActionType.FORWARD)) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(action.get());
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + action.get());
            }
        }
    }

    private CommandFactory createCommandFactory(Connection connection){
        DaoFactory daoFactory = new DaoFactory(connection);
        ServiceFactory serviceFactory = new ServiceFactory(daoFactory);
        return new CommandFactory(serviceFactory);
    }
}
