package by.epam.dmitriytomashevich.javatr.courses.filter;


import by.epam.dmitriytomashevich.javatr.courses.command.Command;
import by.epam.dmitriytomashevich.javatr.courses.command.CommandEnum;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;
import by.epam.dmitriytomashevich.javatr.courses.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class AuthorizationFilter implements Filter {
    private Set<String> userCommands = new HashSet<>();
    private Set<String> adminCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userCommands.add(CommandEnum.GREETING.getName());
        userCommands.add(CommandEnum.HELP.getName());
        userCommands.add(CommandEnum.LOG_OUT.getName());
        userCommands.add(CommandEnum.PAGE_NOT_FOUND.getName());
        userCommands.add(CommandEnum.MAIN.getName());

        userCommands.add(CommandEnum.AJAX_SEND_MESSAGE.getName());
        userCommands.add(CommandEnum.VIEW_MORE.getName());
        userCommands.add(CommandEnum.UPLOAD_MESSAGES.getName());

        userCommands.add(CommandEnum.VIEW_MORE_CONFERENCES.getName());
        userCommands.add(CommandEnum.SEND_REQUEST.getName());
        userCommands.add(CommandEnum.LOAD_MESSAGES.getName());


        adminCommands.add(CommandEnum.GREETING.getName());
        adminCommands.add(CommandEnum.LOG_OUT.getName());
        adminCommands.add(CommandEnum.PAGE_NOT_FOUND.getName());
        adminCommands.add(CommandEnum.MAIN.getName());

        adminCommands.add(CommandEnum.ADMIN_CONVERSATION_COMMAND.getName());
        adminCommands.add(CommandEnum.AJAX_SEND_MESSAGE.getName());
        adminCommands.add(CommandEnum.VIEW_MORE.getName());
        adminCommands.add(CommandEnum.UPLOAD_MESSAGES.getName());

        adminCommands.add(CommandEnum.ADMIN_PAGE.getName());
        adminCommands.add(CommandEnum.ADD_CONFERENCE.getName());
        adminCommands.add(CommandEnum.CREATE_CONFERENCE.getName());
        adminCommands.add(CommandEnum.PREVIEW_COURSE_CONTENT.getName());

        adminCommands.add(CommandEnum.EDIT_CONFERENCE_CONTENT.getName());
        adminCommands.add(CommandEnum.DELETE_CONFERENCE.getName());

        adminCommands.add(CommandEnum.VIEW_MORE_CONFERENCES.getName());
        adminCommands.add(CommandEnum.LOAD_MESSAGES.getName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(isStaticPicture(request.getRequestURI(), request)){
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(request.getRequestURI());
            dispatcher.forward(request, response);
            return;
        }

        HttpSession currentSession = request.getSession(false);
        if (currentSession != null && currentSession.getAttribute(Parameter.USER) != null) {
            User user = (User) request.getSession().getAttribute(Parameter.USER);
            boolean isAdmin = user.isAdmin();

            String command = null;
            if (request.getMethod().equals(Parameter.METHOD_GET)) {
                try {
                    command = CommandEnum.fromValue(request.getRequestURI()).getName();
                } catch (IllegalArgumentException e) {
                    response.sendRedirect(request.getContextPath() + JSP.GREETING_ACTION);
                    return;
                }
            } else if ((command = request.getParameter(Parameter.COMMAND)) == null) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(JSP.PAGE_NOT_FOUND_ACTION);
                dispatcher.forward(request, response);
                //response.sendRedirect(request.getContextPath() + JSP.PAGE_NOT_FOUND_ACTION);
                //response.sendRedirect(request.getContextPath() + JSP.GREETING_ACTION);
                return;
            }

            if ((isAdmin && adminCommands.contains(command)) || userCommands.contains(command)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + JSP.PAGE_NOT_FOUND_ACTION);
            }
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isStaticPicture(String uri, HttpServletRequest request){
        return uri.startsWith(Parameter.STATIC_IMAGES_PATH) || uri.startsWith("/images/tmp");
    }
}
