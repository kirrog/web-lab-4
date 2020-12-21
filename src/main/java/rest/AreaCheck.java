package rest;


import beansLab.entities.Shot;
import beansLab.entities.User;
import resources.UserManager;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("faces/checkShot")
public class AreaCheck {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AreaCheck.class);

    @EJB
    private UserManager ejb;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getShots(@Context HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("User");
        List<Shot> shots = user.getShots();

        StringBuilder stringBuilder = new StringBuilder();

        log.info("Write them all");
        for (int i = shots.size() - 1; i >= 0; i--) {
            String string = formSCV(shots.get(i));
            stringBuilder.append(string).append("\n");
        }

        log.info("Response written");

        return Response
                .ok()
                .entity(stringBuilder.toString())
                .header("rows", shots.size())
                .build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkShot(@Context HttpServletRequest request){


        User user = (User) request.getSession().getAttribute("User");
        List<Shot> shots = user.getShots();

        Response.ResponseBuilder response = Response.ok();

        log.info("User: " + user.getLogin());
        log.info("Rows: " + shots.size());

        log.info("Add one shot");

        String xStr = request.getParameter("coord_x");
        String yStr = request.getParameter("coord_y");
        String rStr = request.getParameter("coord_r");

        log.info("X: " + xStr);
        log.info("Y: " + yStr);
        log.info("R: " + rStr);

        double x = 0;
        double y = 0;
        double r = 0;

        try {
            x = Double.parseDouble(xStr);
            y = Double.parseDouble(yStr);
            r = Double.parseDouble(rStr);
        } catch (Exception e) {
            log.info(e.getMessage());
            response.status(403);
            return response.build();
        }

        Shot shot = addShot(x, y, r, user);

        log.info("Write just one");

        String string = formSCV(shot);

        log.info("Response written");

        return response
                .entity(string)
                .header("rows", shots.size())
                .build();
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteShots(@Context HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("User");
        List<Shot> shots = user.getShots();

        ejb.clearUser(user);

        log.info("Response written");

        return Response
                .ok()
                .header("rows", shots.size())
                .build();
    }

    private Shot addShot(double x, double y, double r, User user) {

        long m = System.nanoTime();

        LocalDateTime start = LocalDateTime.now();

        boolean res = check(x, y, r);

        long time = System.nanoTime() - m;

        Shot shot = new Shot();
        shot.setX(x);
        shot.setY(y);
        shot.setR(r);
        shot.setRG(res);
        shot.setStart(start);
        shot.setScriptTime(time);

        ejb.addShotToUser(user, shot);

        return shot;
    }

    private String formSCV(Shot shot) {
        return cut(shot.getX()) + " " + cut(shot.getY()) + " " + cut(shot.getR()) + " " + shot.isRG() + " " + shot.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy;HH:mm:ss")) + " " + ((shot.getScriptTime()) / 1000);
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
        if ((y == 0 && ((x > 0 && x <= r) || ((-x) <= r / 2))) || (x == 0 && (((y > 0) && (y <= r)) || ((-y) <= r / 2)))) {
            return true;
        }
        if (y > 0) {
            if (x > 0) {
                return false;
            } else {
                return ((-x * 2) + y <= r);
            }
        } else {
            if (x > 0) {
                return ((x <= r) && (-y * 2 <= r));
            } else {
                return (x * x + y * y <= (r * r) / 4);
            }
        }
    }
}
