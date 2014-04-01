package fr.n0rad.hands.on.hystrix.t99.voicemail;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/")
public interface Voicemail {

    enum CallType {
        DEPOSIT, CONSULT;
    }

    @GET
    @Path("/call")
    String getWelcome(@QueryParam("recipient") String recipient,
                      @QueryParam("guest") String guest,
                      @QueryParam("type") CallType type);

    @POST
    @Path("/voicemails")
    String depositVoicemail(@QueryParam("recipient") String recipient,
                            @QueryParam("guest") String guest,
                            String message);

    @GET
    @Path("/voicemails")
    String consultMessages(@QueryParam("recipient") String recipient);
}
