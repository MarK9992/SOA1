package fr.unice.polytech.soa1.volley.orders;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @Path("/{orderRef}/{status}")
    @PUT
    void updateStatus(@PathParam("orderRef") String orderReference, @PathParam("status") OrderStatus status);
}
