package fr.unice.polytech.soa1.volley.catalog;

import fr.unice.polytech.soa1.volley.customexceptions.ConflictException;
import fr.unice.polytech.soa1.volley.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

/**
 * @author Marc Karassev
 */
@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogService {

    Storage<VolleyStuff> storage;

    public CatalogService() {
        storage = VolleyStuffStorageMock.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createNewVolleyStuff(List<VolleyStuff> stuffToCreateList) {
        if (stuffToCreateList == null) {
            throw new BadRequestException("no POST data");
        }
        for (VolleyStuff stuff: stuffToCreateList) {
            if(storage.read(stuff.getName()) != null) {
                throw new ConflictException("\"Existing name " + stuff.getName() + "\"");
            }
            storage.create(stuff);
        }
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
