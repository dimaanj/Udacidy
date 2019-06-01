package by.epam.dmitriytomashevich.javatr.courses.filter;

import by.epam.dmitriytomashevich.javatr.courses.constant.ActionNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.CommandNames;
import by.epam.dmitriytomashevich.javatr.courses.constant.ParameterNames;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;
import by.epam.dmitriytomashevich.javatr.courses.util.validator.CommandValidator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class CommandFilter implements Filter {
    private Set<String> guestAllowedCommands = new HashSet<>();
    private Set<String> userCommands = new HashSet<>();
    private Set<String> adminCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        guestAllowedCommands.add(CommandNames.GREETING);
        guestAllowedCommands.add(CommandNames.REGISTRATION);
        guestAllowedCommands.add(CommandNames.LOGIN);
        guestAllowedCommands.add(CommandNames.CHANGE_LOCALE);

        userCommands.add(CommandNames.USER_PAGE);
        userCommands.add(CommandNames.HELP);
        userCommands.add(CommandNames.LOG_OUT);
        userCommands.add(CommandNames.PAGE_NOT_FOUND);
        userCommands.add(CommandNames.AJAX_SEND_MESSAGE);
        userCommands.add(CommandNames.VIEW_MORE);
        userCommands.add(CommandNames.UPLOAD_MESSAGES);
        userCommands.add(CommandNames.VIEW_MORE_CONFERENCES);
        userCommands.add(CommandNames.LOAD_MESSAGES);
        userCommands.add(CommandNames.REMOVE_REQUEST);
        userCommands.add(CommandNames.CONFERENCES);
        userCommands.add(CommandNames.CHANGE_PASSWORD);
        userCommands.add(CommandNames.GET_CONFERENCE_CONTENT);
        userCommands.add(CommandNames.GET_PAGE_CONTENT);
        userCommands.add(CommandNames.CHANGE_LOCALE);
        userCommands.add(CommandNames.SEND_CLIENT_REQUEST);

        adminCommands.add(CommandNames.LOG_OUT);
        adminCommands.add(CommandNames.PAGE_NOT_FOUND);
        adminCommands.add(CommandNames.ADMIN_CONVERSATION);
        adminCommands.add(CommandNames.AJAX_SEND_MESSAGE);
        adminCommands.add(CommandNames.VIEW_MORE);
        adminCommands.add(CommandNames.UPLOAD_MESSAGES);
        adminCommands.add(CommandNames.ADMIN_PAGE);
        adminCommands.add(CommandNames.ADD_CONFERENCE);
        adminCommands.add(CommandNames.CREATE_CONFERENCE);
        adminCommands.add(CommandNames.PREVIEW_COURSE_CONTENT);
        adminCommands.add(CommandNames.EDIT_CONFERENCE_CONTENT);
        adminCommands.add(CommandNames.DELETE_CONFERENCE);
        adminCommands.add(CommandNames.VIEW_MORE_CONFERENCES);
        adminCommands.add(CommandNames.LOAD_MESSAGES);
        adminCommands.add(CommandNames.CONTENT_EDITING);
        adminCommands.add(CommandNames.GET_PAGE_CONTENT);
        adminCommands.add(CommandNames.CHANGE_LOCALE);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession currentSession = request.getSession(false);
        Boolean isAdmin = null;
        User user = currentSession != null ? (User) currentSession.getAttribute(ParameterNames.USER) : null;
        if (user != null) {
            isAdmin = user.isAdmin();
        }

        String stringCommand;
        if (request.getMethod().equals(ParameterNames.METHOD_GET)) {
            stringCommand = request.getRequestURI();
        } else {
            stringCommand = request.getParameter(ParameterNames.COMMAND);
        }

        if ((user == null && CommandValidator.defineCommand(guestAllowedCommands, stringCommand)) ||
                (isAdmin != null && isAdmin && CommandValidator.defineCommand(adminCommands, stringCommand)) ||
                (isAdmin != null && !isAdmin && CommandValidator.defineCommand(userCommands, stringCommand))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(ActionNames.PAGE_NOT_FOUND_ACTION);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
