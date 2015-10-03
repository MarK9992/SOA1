package fr.unice.polytech.soa1.volley.orders;

import fr.unice.polytech.soa1.volley.accounts.Account;

import javax.ws.rs.*;
import java.util.Collection;

/**
 * @author : Laureen Ginier
 */
public interface OrderService {

    @Path("/{orderRef}")
    @GET
    Orders getOrderByRef(@PathParam("orderRef") String reference);

    @Path("/{customerId}")
    @GET
    Collection<Orders> getOrdersByAccount(@PathParam("customerId") String customerId);

    @GET
    Collection<Orders> getOrders();

    @Path("/{orderRef/status}")
    @PUT
    void updateStatus(@PathParam("orderRef") String orderReference, @PathParam("status") Status status);
}
