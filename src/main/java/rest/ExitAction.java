package rest;

import resources.UserManager;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("faces/exit")
public class ExitAction {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExitAction.class);

    @EJB
    private UserManager ejb;

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response completeExit(@Context HttpServletRequest request){
        log.info("Get exit");
        ejb.endSession(request.getSession());
        log.info("Complete exit");
        return Response
                .status(200)
                .header("result", 0)
                .build();
    }

}
