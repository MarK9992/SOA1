package fr.unice.polytech.soa1.volley.basket;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

/**
 * @author Marc Karassev
 */
public interface BasketService {

    @Path("/{login}")
    @GET
    List<BasketItem> getBasket(@PathParam("login") String login);

    @Path("/{login}")
    @POST
    void addProductToBasket(@PathParam("login") String login, List<BasketItem> products);

    @Path("/{login}")
    @PUT
    void adjustQuantity(@PathParam("login") String login, BasketItem item);
}
