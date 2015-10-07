package fr.unice.polytech.soa1.volley.orders;

/**
 * @author : Laureen Ginier
 */
public enum OrderStatus {

    PAID("Order paid"),
    PREPARATION("Order in preparation"),
    SHIPPED("Order shipped"),
    DELIVERING("Under delivery"),
    DELIVERED("Order delivered"),
    CANCELED("Order canceled"),
    DELAYED("Delivery exception");

    private String description;

    OrderStatus(String description){
        this.description = description;
    }

    public String getDescription(){ return this.description; }

    @Override
    public String toString(){ return this.description; }
}
