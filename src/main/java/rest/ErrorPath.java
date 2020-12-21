package rest;

import resources.UserManager;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@Path("/")
public class ErrorPath {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ErrorPath.class);

    @EJB
    private UserManager ejb;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkUser(){
        return "Error!!!";
    }

}
