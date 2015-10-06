package fr.unice.polytech.soa1.volley.basket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.soa1.volley.catalog.Color;

/**
 * @author Marc Karassev
 *
 * Utility class used to deserialize JSON data received by the BasketServiceImpl class.
 */
public class BasketItem {

    // Attributes

    private String name;
    private int quantity;
    private Color color;

    // Constructors

    @JsonCreator
    public BasketItem(@JsonProperty(value = "name", required = true) String name,
                      @JsonProperty(value = "quantity", required = true) int quantity,
                      @JsonProperty("color") Color color) {
        this.name = name;
        this.quantity = quantity;
        this.color = color;
    }

    // Methods

    @Override
    public String toString() {
        return name + " " + color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasketItem)) return false;

        BasketItem that = (BasketItem) o;

        return name.equals(that.name) && color == that.color;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Color getColor() {
        return color;
    }
}
