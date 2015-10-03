package fr.unice.polytech.soa1.volley.payments;

import fr.unice.polytech.soa1.volley.orders.Orders;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Collection;

public interface PaymentService {

    @Path("/{accountId}")
    @POST
    Orders pay(@PathParam("accountId") String accountId, Payment payment);

    @GET
    Collection<Payment> getPayments();

    @Path("/{accountId}")
    @GET
    Collection<Payment> getPaymentsByAccount(@PathParam("accountId") String customerId);

    @Path("/{paymentId}")
    @GET
    Payment getPaymentById(@PathParam("paymentId") String paymentId);

}
