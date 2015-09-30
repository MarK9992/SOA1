package fr.unice.polytech.soa1.volley.catalog;

import fr.unice.polytech.soa1.volley.customexceptions.ConflictException;
import fr.unice.polytech.soa1.volley.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

/**
 * @author Marc Karassev
 *         <p>
 *         Catalog service, provides a REST API allowing to list, show, create, update or delete volley equipments
 *         available on the store.
 *
 *         TODO security for catalog management operations?
 */
@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogService {

    Storage<VolleyStuff> storage;

    public CatalogService() {
        storage = VolleyStuffStorageMock.getInstance();
    }

    /**
     * Adds new volley stuffs to catalog.
     * Eats an array of JSON objects representing VolleyStuff objects, see VolleyStuff documentation.
     *
     * @param stuffToCreateList the deserialized list of VolleyStuff instances
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createNewVolleyStuff(List<VolleyStuff> stuffToCreateList) {
        if (stuffToCreateList == null) {
            throw new BadRequestException("no POST data");
        }
        for (VolleyStuff stuff : stuffToCreateList) {
            if (storage.read(stuff.getName()) != null) {
                throw new ConflictException("\"Existing name " + stuff.getName() + "\"");
            }
            storage.create(stuff);
        }
    }

    /**
     * Gets all volley stuffs available in the store.
     *
     * @return a array of JSON objects representing VolleyStuff instances, see VolleyStuff documentation.
     */
    @GET
    public Collection<VolleyStuff> getAvailableVolleyStuff() {
        return storage.findAll();
    }

    /**
     * Gets the VolleyStuff object matching the given name.
     *
     * @param name the name given as path parameter to look for
     * @return a JSON object representing a VolleyStuff object, see VolleyStuff documentation
     */
    @Path("/{name}")
    @GET
    public VolleyStuff getThatStuff(@PathParam("name") String name) {
        if (storage.read(name) == null) {
            throw new NotFoundException();
        }
        return storage.read(name);
    }

    @Path("/{name}")
    @DELETE
    public void deleteVolleyStuff(@PathParam("name") String name) {
        if (storage.read(name) == null) {
            throw new NotFoundException();
        }
        storage.delete(name);
    }

}
