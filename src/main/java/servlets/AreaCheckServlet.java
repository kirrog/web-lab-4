package servlets;

import beansLab.entities.Shot;
import beansLab.entities.User;
import resources.UserManager;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/checkShot")
public class AreaCheckServlet extends HttpServlet {
    @EJB
    private UserManager ejb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int shotsSize = request.getIntHeader("shotsSize");

        long m = System.nanoTime();

        LocalDateTime start = LocalDateTime.now();

        double x = Double.parseDouble(request.getParameter("answerX"));
        double y = Double.parseDouble(request.getParameter("answerY"));
        double r = Double.parseDouble(request.getParameter("answerR"));

        boolean res = check(x, y, r);

        long time = System.nanoTime() - m;

        Shot shot = new Shot();
        shot.setX(x);
        shot.setY(y);
        shot.setR(r);
        shot.setRG(res);
        shot.setStart(start);
        shot.setX(time);

        User user = (User)request.getSession().getAttribute("User");

        ejb.addShotToUser(user, shot);

        List<Shot> shots = user.getShots();

        response.setIntHeader("rows", shots.size());

        if(shotsSize > 0){
            for (int i = shots.size() - 1; i > 0; i--) {
                response.getWriter().println(formSCV(shots.get(i)));
            }
        }else {
            response.getWriter().println(formSCV(shot));
        }
    }

    private String formSCV(Shot shot){
        return cut(shot.getX()) + " " + cut(shot.getY()) + " " + cut(shot.getR()) + " " + shot.isRG() + " " + shot.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy;hh:mm:ss")) + " " + ((shot.getScriptTime()) / 1000);
    }

    private String cut(double num) {
        String result;
        if (num >= 0) {
            if (String.valueOf(num).length() > 7) {
                result = (String.valueOf(num)).substring(0, 6);
            } else {
                result = String.valueOf(num);
            }
        } else {
            if (String.valueOf(num).length() > 8) {
                result = (String.valueOf(num)).substring(0, 7);
            } else {
                result = String.valueOf(num);
            }
        }
        return result;
    }

    //Previously checked initialization and intervals
    public boolean check(double x, double y, double r) {
        if (y >= 0) {
            if (x > 0) {
                return ((x + 2 * y) <= r);
            } else {
                return ((-x <= r) && (y <= r / 2));
            }
        } else {
            if (x > 0) {
                return false;
            } else {
                return (x * x + y * y <= r * r);
            }
        }
    }
}
