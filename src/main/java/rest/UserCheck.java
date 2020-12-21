package rest;

import resources.UserInf;
import resources.UserManager;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;


@Path("checkUser")
@Singleton
public class UserCheck {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserCheck.class);

    @EJB
    private UserManager ejb;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkUser(){
        return "Checking user";
    }

    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response postNotification(@Context HttpServletRequest req) {
        log.info("StartChecking");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        log.info("Login: " + login + " Password: " + password);
        try {
            int result = ejb.checkUser(login, password);
            log.info("Checked " + result);
            switch (result) {
                case 0: { //successful login
                    Response.ResponseBuilder response = Response.status(200, "ok");
                    UserInf userInf = ejb.loginUser(login, password, req.getSession());
                    log.info((String.valueOf(userInf.getToken())));
                    Cookie cookie = new Cookie("token", String.valueOf(userInf.getToken()));
                    response.cookie(new NewCookie(cookie, "", -1 , false));
                    response.header("StatusOfLogIn", 0);
                    log.info("Completed");
                    return response.build();
                }
                case 1: { //wrong login
                    Response.ResponseBuilder response = Response.status(200, "ok");
                    response.header("StatusOfLogIn", 1);
                    log.info("Wrong login");
                    return response.build();
                }
                case 2: { //wrong password
                    Response.ResponseBuilder response = Response.status(200, "ok");
                    response.header("StatusOfLogIn", 2);
                    log.info("Wrong password");
                    return response.build();
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return Response
                .status(200)
                .header("StatusOfLogIn", -1)
                .build();
    }

}
