package servlets;

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

@WebServlet("/checkUser")
public class CheckUserServlet extends HttpServlet {
    @EJB
    private UserManager ejb;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            int result = ejb.checkUser(login, password);
            switch (result){
                case 0:{ //successful login
                    resp.setIntHeader("StatusOfLogIn", 0);
                    UserInf userInf = ejb.loginUser(login, password, req.getSession());
                    Cookie cookie = new Cookie("token", String.valueOf(userInf.getToken()));
                    cookie.setMaxAge(-1);
                    resp.addCookie(cookie);
                }
                case 1:{ //wrong login
                    resp.setIntHeader("StatusOfLogIn", 1);

                }
                case 2:{ //wrong password
                    resp.setIntHeader("StatusOfLogIn", 2);

                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
