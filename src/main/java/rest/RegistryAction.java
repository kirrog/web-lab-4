package rest;

import beansLab.entities.User;
import resources.UserInf;
import resources.UserManager;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;


@Path("register")
public class RegistryAction {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RegistryAction.class);

    @EJB
    private UserManager ejb;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String registerPerson(){
        return "Completing registration";
    }

    @POST
    //@Consumes(MediaType.TEXT_PLAIN)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response postNotification(@Context HttpServletRequest req) {
        log.info("Got request");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        log.info("Login: " + login + " Password: " + password);

        boolean res = ejb.addUser(new User(login, password));

        log.info("Result of registration: " + res);

        Response.ResponseBuilder response = Response.status(200, "ok");

        if(res){
            response.header("StatusOfRegistration", 1);
        }else {
            response.header("StatusOfRegistration", 0);
        }

        return response.build();
    }

}
