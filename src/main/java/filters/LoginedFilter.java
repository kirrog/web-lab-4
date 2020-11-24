package filters;

import resources.UserManager;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "SessionFilter")
public class LoginedFilter implements Filter {
    @EJB
    private UserManager ejb;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest)servletRequest).getRequestURI().toString();
        if(uri == "/Lab4/index.html"){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if (ejb.hasSession(((HttpServletRequest)servletRequest).getSession())){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            String path = "/index.html";
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(path);
            requestDispatcher.forward(servletRequest, servletResponse);
        }
    }

}
