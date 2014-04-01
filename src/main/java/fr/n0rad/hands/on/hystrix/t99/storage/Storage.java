package fr.n0rad.hands.on.hystrix.t99.storage;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import fr.n0rad.hands.on.hystrix.Greeting;

@Path("/")
public interface Storage {
    @GET
    @Path("/recipients/{recipient}/greetings")
    Greeting getGreeting(@PathParam("recipient") String recipient,
                         @QueryParam("caller") String caller) throws UserNotFoundException;

    @POST
    @Path("/recipients/{recipient}/voicemail")
    void postVoicemail(@PathParam("recipient") String recipient,
                       @QueryParam("caller") String caller,
                       String text) throws UserNotFoundException;

    @GET
    @Path("/recipients/{recipient}/voicemail")
    List<Message> getMessages(@PathParam("recipient") String recipient) throws UserNotFoundException;

    @DELETE
    @Path("/voicemail")
    void clear();
}
