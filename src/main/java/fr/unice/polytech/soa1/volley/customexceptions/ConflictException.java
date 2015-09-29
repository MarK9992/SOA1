package fr.unice.polytech.soa1.volley.customexceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Marc Karassev
 */
public class ConflictException extends ClientErrorException {

    public ConflictException(String message) {
        super(Response.status(Response.Status.CONFLICT).entity(message).type(MediaType.TEXT_PLAIN_TYPE).build());
    }
}
