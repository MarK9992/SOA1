package fr.unice.polytech.soa1.volley;

import org.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/stuff")
// Here we generate JSON data from scratch, one should use a framework instead
@Produces(MediaType.APPLICATION_JSON)
public class VolleyStuffService {

    Storage storage;

    public VolleyStuffService() {
        storage = StorageMock.getInstance();
    }

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response createNewVolleyStuff(String name) {
	    if(storage.read(name) != null) {
			return Response.status(Response.Status.CONFLICT)
					       .entity("\"Existing name " + name + "\"")
					       .build();
		}
        storage.create(name);
		return Response.ok().build();
	}

	@GET
	public Response getAvailableVolleyStuff() {
		Collection<VolleyStuff> gens = storage.findAll();
		JSONArray result = new JSONArray();
		for(VolleyStuff g: gens) {
			result.put(g.getName());
		}
		return Response.ok().entity(result.toString(2)).build();
	}


	@Path("/{name}")
	@GET
	public Response generateIdentifier(@PathParam("name") String name) {
		if(storage.read(name) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		String value = storage.read(name).run();
		return Response.ok().entity("\""+value+"\"").build();
	}

	@Path("/{name}")
	@DELETE
	public Response deleteGenerator(@PathParam("name") String name) {
		if(storage.read(name) == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
        storage.delete(name);
		return Response.ok().build();
	}

}
