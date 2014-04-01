package fr.n0rad.hands.on.hystrix.t99.storage;

import javax.ws.rs.core.Response.Status;
import fr.norad.jaxrs.client.server.api.HttpStatus;

@HttpStatus(Status.NOT_FOUND)
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
