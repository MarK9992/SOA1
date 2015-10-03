package fr.unice.polytech.soa1.volley.payments;

import fr.unice.polytech.soa1.volley.Storage;
import fr.unice.polytech.soa1.volley.accounts.Account;
import fr.unice.polytech.soa1.volley.accounts.AccountStorageMock;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuff;
import fr.unice.polytech.soa1.volley.catalog.VolleyStuffStorageMock;
import fr.unice.polytech.soa1.volley.orders.OrderStorageMock;
import fr.unice.polytech.soa1.volley.orders.Orders;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
        Map<String, Integer> basket = account.getBasket();
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
        Payment finalPayment = new Payment(accountId, address, amount,order.getRef(), cardNumber, cryptogram, validityDate);
        paymentStorage.create(finalPayment);
        orderStorage.create(order);
        account.emptyBasket();
        return order;
    }

    private boolean validatePayment(long cardNumber){
        if(cardNumber % 2 == 0) {
            return true;
        }
        return false;
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

    private double calculateAmount(Map<String, Integer> basket){
        double amount = 0;
        for(Map.Entry<String, Integer> entry : basket.entrySet()) {
            amount += volleyStuffStorage.read(entry.getKey()).getPrice() * entry.getValue();
        }
        return amount;
    }
}
