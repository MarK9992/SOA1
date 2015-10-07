package fr.unice.polytech.soa1.volley.orders;

import fr.unice.polytech.soa1.volley.basket.BasketItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Laureen Ginier.
 *
 */
public class Orders {

    // Attributes

    private static long orderCpt = 0;
    private String ref;
    private OrderStatus status;
    private String customer;
    private String deliveryAddress;
    private double amount;
    private List<BasketItem> items;

    // Constructors

    public Orders(String accountName,
                  List<BasketItem> basket,
                  double amount,
                  String address){
        ++orderCpt;
        this.ref = String.valueOf(orderCpt);
        this.customer = accountName;
        this.deliveryAddress = address;
        this.status = OrderStatus.PAID;
        this.amount = amount;
        this.items = new ArrayList<BasketItem>();
        this.items.addAll(basket);
    }

    // Methods

    @Override
    public String toString() {
        return "{ " + ref + ", " + customer + ", " + deliveryAddress+ ", " + amount + ", " + items + ", " + status + " }";
    }

    // Getters and setters

    public static long getOrderCpt() { return orderCpt; }

    public String getRef() { return ref; }

    public OrderStatus getState() { return status; }

    public void setStatus(OrderStatus newStatus) { this.status = newStatus; }

    public double getAmount() { return amount; }

    public List<BasketItem> getItems() { return items; }

    public String getCustomer(){ return this.customer; }
}
