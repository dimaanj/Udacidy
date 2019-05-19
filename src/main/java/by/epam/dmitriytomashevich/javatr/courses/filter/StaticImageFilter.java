package by.epam.dmitriytomashevich.javatr.courses.filter;

import by.epam.dmitriytomashevich.javatr.courses.constant.Parameter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticImageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (isStaticPicture(request.getRequestURI())) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(request.getRequestURI());
            dispatcher.forward(request, response);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean isStaticPicture(String uri){
        return uri.startsWith(Parameter.STATIC_IMAGES_PATH) || uri.startsWith(Parameter.TMP_IMAGES_PATH);
    }
}
