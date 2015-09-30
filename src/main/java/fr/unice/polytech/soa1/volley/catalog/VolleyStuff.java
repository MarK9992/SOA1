package fr.unice.polytech.soa1.volley.catalog;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marc Karassev
 *         <p>
 *         For deserialization, the JSON representation has two required properties "name" (string) and "price" (number)
 *         and one optionnal property "description" (string).
 *         { "name": "...", "price": xx.xx } or { "name": "...", "price": xx.xx, "description": "..." }
 *         For serialization, the "description" property becomes mandatory.
 */
public class VolleyStuff {

    // Attributes

    private String name;
    private double price;
    private String description;
    private int cpt = 0;

    // Constructors

    public VolleyStuff(String name, double price) {
        this(name, price, "");
    }

    @JsonCreator
    public VolleyStuff(@JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "price", required = true) double price,
                       @JsonProperty("description") String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Methods

    public String run() {
        cpt++;
        return name + cpt;
    }

    @Override
    public String toString() {
        return "{ " + name + ", " + price + ", " + description + " }";
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @JsonIgnore
    public int getCpt() {
        return cpt;
    }

}
