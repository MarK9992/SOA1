package fr.unice.polytech.soa1.volley.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author : Laureen Ginier.
 *
 */
public class Orders {

    // Attributes

    private static long orderCpt = 0;
    private String ref;
    private String status;
    private String customer;
    private String deliveryAddress;
    private double amount;
    private Map<String, Integer> items;

    // Constructors

    @JsonCreator
    public Orders(@JsonProperty(value = "customer", required = true) String accountName,
                  @JsonProperty(value = "basket", required = true) Map<String, Integer> basket,
                  @JsonProperty(value = "amount") double amount,
                  @JsonProperty(value = "address") String address){
        ++orderCpt;
        this.ref = String.valueOf(orderCpt);
        this.customer = accountName;
        this.deliveryAddress = address;
        this.status = Status.PAID.getDescription();
        this.amount = amount;
        this.items = basket;
    }

    // Methods

    @Override
    public String toString() {
        return "{ " + ref + ", " + customer + ", " + amount + ", " + amount + ", " + status + " }";
    }

    // Getters and setters

    public static long getOrderCpt() { return orderCpt; }

    public String getRef() { return ref; }

    public String getState() { return status; }

    public double getAmount() { return amount; }

    public Map<String, Integer> getItems() { return items; }

    public String getCustomer(){ return this.customer; }
}
