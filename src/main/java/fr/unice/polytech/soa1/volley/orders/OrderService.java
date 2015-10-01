package fr.unice.polytech.soa1.volley.orders;

import fr.unice.polytech.soa1.volley.accounts.Account;

import javax.ws.rs.*;
import java.util.Collection;

/**
 * @author : Laureen Ginier
 */
public interface OrderService {

    @Path("/{accountId}")
    @POST
    Orders pay(@PathParam("accountId") String accountId, String address);

    @Path("/{orderRef}")
    @GET
    Orders getOrder(@PathParam("orderRef") String reference);

    @Path("/{customerId}")
    @GET
    Collection<Orders> getOrdersByAccount(@PathParam("customerId") String customerId);

    @GET
    Collection<Orders> getOrders();

    @Path("/{orderRef}")
    @PUT
    void updateOrder(@PathParam("orderRef") String reference, Orders theOrder);

}
