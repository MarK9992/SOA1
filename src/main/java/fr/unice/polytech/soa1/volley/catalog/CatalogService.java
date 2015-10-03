package fr.unice.polytech.soa1.volley.catalog;

import fr.unice.polytech.soa1.volley.customexceptions.ConflictException;
import fr.unice.polytech.soa1.volley.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
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

    private Storage<VolleyStuff> storage;

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

    /**
     * Gets the colors available for the VolleyStuff object matching the given name.
     *
     * @param name the name given as path parameter to look for
     * @return a JSON array containing the available colors
     */
    @Path("/{name}/colors")
    @GET
    public List<Color> getAvailableColors(@PathParam("name") String name) {
        if (storage.read(name) == null) {
            throw new NotFoundException();
        }
        return Arrays.asList(Color.values());
    }

    /**
     * Removes the volley stuff from the catalog matching the given name.
     *
     * @param name the name given as path parameter to look for
     */
    @Path("/{name}")
    @DELETE
    public void deleteVolleyStuff(@PathParam("name") String name) {
        if (storage.read(name) == null) {
            throw new NotFoundException();
        }
        storage.delete(name);
    }

    // TODO delete several ?

    /**
     * Updates the volley stuff in the store matching the given name.
     *
     * @param name the name given as path parameter to look for
     * @param update the deserialized VolleyStuff object to use as update
     */
    @Path("/{name}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateStuff(@PathParam("name") String name, VolleyStuff update) {
        if (update == null) {
            throw new BadRequestException("no PUT data");
        }
        if (storage.read(name) == null) {
            throw new NotFoundException();
        }
        storage.delete(name);
        storage.create(update);
    }

}
