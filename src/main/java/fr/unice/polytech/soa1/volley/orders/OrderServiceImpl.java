package fr.unice.polytech.soa1.volley.orders;

import fr.unice.polytech.soa1.volley.Storage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author : Laureen Ginier
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderServiceImpl implements OrderService {

    private Storage<Orders> orderStorage;

    public OrderServiceImpl() {
        orderStorage = OrderStorageMock.getInstance();
    }

    @Path("/order/{orderRef}")
    @GET
    public Orders getOrderByRef(@PathParam("orderRef") String reference) {
        Orders order = orderStorage.read(reference);

        if(order == null){
            throw new NotFoundException();
        }
        return order;
    }

    @Path("/account/{customerId}")
    @GET
    public Collection<Orders> getOrdersByAccount(@PathParam("customerId") String customerId) {
        Collection<Orders> customerOrders = new ArrayList<Orders>();
        Collection<Orders> allOrders = orderStorage.findAll();

        for(Orders order : allOrders){
            if(order.getCustomer().equals(customerId)){
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    public Collection<Orders> getOrders() { return orderStorage.findAll(); }

    @Path("/{orderRef}/{status}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateStatus(@PathParam("orderRef") String orderReference, @PathParam("status") OrderStatus status) {
        Orders order = orderStorage.read(orderReference);
        if(order == null) {
            throw new NotFoundException();
        }
        order.setStatus(status);
        orderStorage.delete(orderReference);
        orderStorage.create(order);
    }
}
