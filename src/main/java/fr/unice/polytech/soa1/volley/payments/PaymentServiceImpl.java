package fr.unice.polytech.soa1.volley.payments;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.accounts.Account;
import fr.unice.polytech.soa1.volley.accounts.AccountStorageMock;
import fr.unice.polytech.soa1.volley.basket.BasketItem;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuff;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuffStorageMock;
import fr.unice.polytech.soa1.volley.orders.OrderStatus;
import fr.unice.polytech.soa1.volley.orders.OrderStorageMock;
import fr.unice.polytech.soa1.volley.orders.Orders;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: Laureen Ginier
 */
@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentServiceImpl implements PaymentService {

    private Storage<Payment> paymentStorage;
    private Storage<Account> accountStorage;
    private Storage<VolleyStuff> volleyStuffStorage;
    private Storage<Orders> orderStorage;

    public PaymentServiceImpl(){
        paymentStorage = PaymentStorageMock.getInstance();
        accountStorage = AccountStorageMock.getInstance();
        volleyStuffStorage = VolleyStuffStorageMock.getInstance();
        orderStorage = OrderStorageMock.getInstance();
    }

    @Path("/{accountId}")
    @POST
    public Orders pay(@PathParam("accountId") String accountId, Payment payment) {
        Account account = accountStorage.read(accountId);
        String address = payment.getAddress();
        String validityDate = payment.getValidityDate();
        long cardNumber = payment.getCardNumber();
        short cryptogram = payment.getCryptogram();
        if(account == null){
            throw new NotFoundException("The given account does not exist");
        }
        List<BasketItem> basket = account.getBasket();
        if(basket.isEmpty()) {
            throw new ForbiddenException("\"The basket is empty\"");
        }
        if ("".equals(address)){
            throw new ForbiddenException("\"The delivery address must not be empty\"");
        }
        if("".equals(validityDate)) {
            throw new ForbiddenException("\"Card information must not be empty\"");
        }

        double amount = calculateAmount(basket);

        if (!validatePayment(cardNumber)){
            throw new BadRequestException("\"The payment failed\"");
        }

        Orders order = new Orders(accountId, account.getBasket(), amount, address);
        Payment finalPayment = new Payment(accountId, address, amount,order.getRef(), cardNumber, cryptogram, validityDate, PaymentStatus.UNCHECKED);
        paymentStorage.create(finalPayment);
        orderStorage.create(order);
        account.emptyBasket();
        return order;
    }

    private boolean validatePayment(long cardNumber){
        return (cardNumber % 2 == 0);
    }

    @GET
    public Collection<Payment> getPayments(){
        return paymentStorage.findAll();
    }

    @Path("/account/{accountId}")
    @GET
    public Collection<Payment> getPaymentsByAccount(@PathParam("accountId") String accountId) {
        Collection<Payment> customerPayments = new ArrayList<Payment>();
        Collection<Payment> allPayments = paymentStorage.findAll();

        for(Payment payment : allPayments){
            if(payment.getCustomer().equals(accountId)){
                customerPayments.add(payment);
            }
        }
        return customerPayments;
    }

    @Path("/payment/{paymentId}")
    @GET
    public Payment getPaymentById(@PathParam("paymentId") String paymentId) {
        Payment payment = paymentStorage.read(paymentId);
        if(payment == null) {
            throw new NotFoundException();
        }
        return payment;
    }

    @Path("/status/{statusId}")
    @GET
    public Collection<Payment> getPaymentsByStatus(@PathParam("statusId") PaymentStatus status) {
        Collection<Payment> allPayments = paymentStorage.findAll();
        Collection<Payment> statusPayments = new ArrayList<Payment>();

        for(Payment payment : allPayments){
            if(payment.getStatus().equals(status)){
                statusPayments.add(payment);
            }
        }
        return statusPayments;
    }

    @Path("/status/{statusId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateStatus(@PathParam("statusId") PaymentStatus status, List<Payment> payments) {
        if (payments == null) {
            throw new BadRequestException("no PUT data");
        }
        for(Payment payment : payments){
            if(payment == null){
                throw new NotFoundException("The given payment does not exist");
            }
            if(status == PaymentStatus.NULLIFY){
                Orders order = orderStorage.read(payment.getOrderReference());
                order.setStatus(OrderStatus.CANCELED);
            }
            if(status == PaymentStatus.APPROVED || status == PaymentStatus.NULLIFY) {
                payment.setStatus(status);
            }
        }
    }

    /*@Path("/{paymentId}/{status}")
    @PUT
    public void updateStatus(@PathParam("paymentId") String paymentId, @PathParam("status") PaymentStatus status) {
        Payment payment = paymentStorage.read(paymentId);
        if(payment == null){
            throw new NotFoundException("The given payment does not exist");
        }
        if(status == PaymentStatus.NULLIFY){
            Orders order = orderStorage.read(payment.getOrderReference());
            order.setStatus(OrderStatus.CANCELED);
        }
        if(status == PaymentStatus.APPROVED || status == PaymentStatus.NULLIFY) {
            payment.setStatus(status);
        }
    }*/

    private double calculateAmount(List<BasketItem> basket){
        double amount = 0;
        for(BasketItem item: basket) {
            amount += volleyStuffStorage.read(item.getName()).getPrice() * item.getQuantity();
        }
        return amount;
    }
}
