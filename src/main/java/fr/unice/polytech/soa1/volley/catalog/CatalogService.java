package fr.unice.polytech.soa1.volley.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.soa1.volley.customexceptions.ConflictException;
import fr.unice.polytech.soa1.volley.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/stuff")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogService {

    Storage<VolleyStuff> storage;

    public CatalogService() {
        storage = VolleyStuffStorageMock.getInstance();
    }

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public void createNewVolleyStuff(String name) {
		if(storage.read(name) != null) {
			throw new ConflictException("\"Existing name " + name + "\"");
		}
		storage.create(name);
	}

	@GET
	public Collection<VolleyStuff> getAvailableVolleyStuff() {
		return storage.findAll();
	}

	@Path("/{name}")
	@DELETE
	public void deleteVolleyStuff(@PathParam("name") String name) {
		if(storage.read(name) == null) {
			throw new NotFoundException();
		}
        storage.delete(name);
	}

    @Path("/{name}")
    @GET
    public String generateIdentifier(@PathParam("name") String name) {
        if(storage.read(name) == null) {
            throw new NotFoundException();
        }
        return storage.read(name).run();
    }

}
