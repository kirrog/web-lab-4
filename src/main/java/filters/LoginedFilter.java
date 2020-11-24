package filters;

import resources.UserManager;
import servlets.CheckUserServlet;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



@WebFilter(filterName = "SessionFilter")
public class LoginedFilter implements Filter {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginedFilter.class);

    @EJB
    private UserManager ejb;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (ejb.hasSession(((HttpServletRequest)servletRequest).getSession())){
            log.info("Filtered and goes to main.jsp");
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            String path = "/Lab4/index.html";
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher(path);
            requestDispatcher.forward(servletRequest, servletResponse);
        }
    }

}
