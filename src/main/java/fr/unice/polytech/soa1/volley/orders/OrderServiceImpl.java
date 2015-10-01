package fr.unice.polytech.soa1.volley.orders;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.accounts.Account;
import fr.unice.polytech.soa1.volley.accounts.AccountStorageMock;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuff;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuffStorageMock;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author : Laureen Ginier
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderServiceImpl implements OrderService {

    private Storage<Orders> orderStorage;
    private Storage<Account> accountStorage;
    private Storage<VolleyStuff> stuffStorage;

    public OrderServiceImpl() {
        orderStorage = OrderStorageMock.getInstance();
        accountStorage = AccountStorageMock.getInstance();
        stuffStorage = VolleyStuffStorageMock.getInstance();
        //pay("admin","Here");
        //pay("admin","There");
    }

    @Path("/{accountId}")
    @POST
    public Orders pay(@PathParam("accountId") String accountId, String address) {
        Account account = accountStorage.read(accountId);
        if(account == null){
            throw new NotFoundException();
        }
        Map<String, Integer> basket = account.getBasket();
        if(basket.isEmpty() || address == ""){
            throw new ForbiddenException("\"The basket is empty\"");
        }
        Orders order = new Orders(accountId, account.getBasket(), calculateAmount(basket), address);
        orderStorage.create(order);
        return order;
    }

    private double calculateAmount(Map<String, Integer> basket){
        double amount = 0;
        for(Map.Entry<String, Integer> entry : basket.entrySet()) {
            amount += stuffStorage.read(entry.getKey()).getPrice() * entry.getValue();
        }
        return amount;
    }

    @Path("/{orderRef")
    @GET
    public Orders getOrder(@PathParam("orderRef") String reference) {
        Orders theOrder = orderStorage.read(reference);

        if(theOrder == null){
            throw new NotFoundException();
        }
        return theOrder;
    }

    @Path("/{customerId}")
    @GET
    public Collection<Orders> getOrdersByAccount(@PathParam("customerId") String customerId) {
        Collection<Orders> customerOrders = new ArrayList<Orders>();
        Collection<Orders> allOrders = orderStorage.findAll();

        for(Orders oneOrder : allOrders){
            if(oneOrder.getCustomer().equals(customerId)){
                customerOrders.add(oneOrder);
            }
        }
        return customerOrders;
    }

    public Collection<Orders> getOrders() { return orderStorage.findAll(); }

    @Path("/{orderRef}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateOrder(@PathParam("orderRef") String reference, Orders theOrder) {
        if(theOrder == null) {
            throw new BadRequestException("no PUT data");
        }
        if(orderStorage.read(reference) == null) {
            throw new NotFoundException();
        }
        orderStorage.delete(reference);
        orderStorage.create(theOrder);
    }
}
