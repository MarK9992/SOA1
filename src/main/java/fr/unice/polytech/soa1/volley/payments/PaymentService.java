package fr.unice.polytech.soa1.volley.payments;

import fr.unice.polytech.soa1.volley.orders.Orders;

import javax.ws.rs.*;
import java.util.Collection;
import java.util.List;

public interface PaymentService {

    @Path("/{accountId}")
    @POST
    Orders pay(@PathParam("accountId") String accountId, Payment payment);

    @GET
    Collection<Payment> getPayments();

    @Path("/account/{accountId}")
    @GET
    Collection<Payment> getPaymentsByAccount(@PathParam("accountId") String customerId);

    @Path("/payment/{paymentId}")
    @GET
    Payment getPaymentById(@PathParam("paymentId") String paymentId);

    @Path("/status/{statusId}")
    @GET
    Collection<Payment> getPaymentsByStatus(@PathParam("statusId") PaymentStatus status);

    @Path("/status/{statusId}")
    @PUT
    void updateStatus(@PathParam("statusId") PaymentStatus status, List<Payment> payments);

}
