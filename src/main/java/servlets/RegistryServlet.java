package servlets;

import beansLab.entities.User;
import resources.UserInf;
import resources.UserManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/register")
public class RegistryServlet extends HttpServlet {
    @EJB
    private UserManager ejb;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean res = ejb.addUser(new User(login, password));
        if(res){
            resp.setIntHeader("Registration", 1);
        }else {
            resp.setIntHeader("Registration", 0);
        }
    }
}
