package fr.unice.polytech.soa1.volley.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Laureen Ginier.
 *
 */
public class Orders {

    // Attributes

    private static long orderCpt = 0;
    private String ref;
    private Status status;
    private String customer;
    private String deliveryAddress;
    private double amount;
    private Map<String, Integer> items;

    // Constructors

    public Orders(String accountName,
                  Map<String, Integer> basket,
                  double amount,
                  String address){
        ++orderCpt;
        this.ref = String.valueOf(orderCpt);
        this.customer = accountName;
        this.deliveryAddress = address;
        this.status = Status.PAID;
        this.amount = amount;
        this.items = new HashMap<String, Integer>();
        this.items.putAll(basket);
    }

    // Methods

    @Override
    public String toString() {
        return "{ " + ref + ", " + customer + ", " + deliveryAddress+ ", " + amount + ", " + items + ", " + status + " }";
    }

    // Getters and setters

    public static long getOrderCpt() { return orderCpt; }

    public String getRef() { return ref; }

    public Status getState() { return status; }

    public void setStatus(Status newStatus) { this.status = newStatus; }

    public double getAmount() { return amount; }

    public Map<String, Integer> getItems() { return items; }

    public String getCustomer(){ return this.customer; }
}
