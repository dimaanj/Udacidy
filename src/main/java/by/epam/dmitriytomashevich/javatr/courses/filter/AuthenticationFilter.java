package by.epam.dmitriytomashevich.javatr.courses.filter;

import by.epam.dmitriytomashevich.javatr.courses.command.CommandEnum;
import by.epam.dmitriytomashevich.javatr.courses.constant.JSP;
import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {
    private Set<String> guestAllowedCommands = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        EnumSet<CommandEnum> guestCommands = EnumSet.range(CommandEnum.GREETING, CommandEnum.LOGIN);
        guestCommands.forEach(commandEnum -> guestAllowedCommands.add(commandEnum.getName()));
        guestAllowedCommands.add(CommandEnum.PAGE_NOT_FOUND.getName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String command = null;
        if (request.getMethod().equals(Parameter.METHOD_GET)) {
            try {
                command = CommandEnum.fromValue(request.getRequestURI()).getName();
            } catch (IllegalArgumentException e) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(JSP.PAGE_NOT_FOUND_ACTION);
                dispatcher.forward(request, response);
                return;
            }
        } else if ((command = request.getParameter(Parameter.COMMAND)) == null) {
            response.sendRedirect(request.getContextPath() + JSP.PAGE_NOT_FOUND_ACTION);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean userInSession = session != null && session.getAttribute(Parameter.USER) != null;

        if (userInSession || guestAllowedCommands.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath() + JSP.PAGE_NOT_FOUND_ACTION);
        }
    }

    @Override
    public void destroy() {

    }
}
