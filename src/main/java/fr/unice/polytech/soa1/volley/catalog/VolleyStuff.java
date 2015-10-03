package fr.unice.polytech.soa1.volley.catalog;


import com.fasterxml.jackson.annotation.JsonCreator;
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

    private static int cpt = 0;

    // Attributes

    private String name;
    private double price;
    private String description;
    private Color color;

    // Constructors

    public VolleyStuff() {
        this("stuff_" + cpt, 0.0, "", Color.BLACK);
    }

    @JsonCreator
    public VolleyStuff(@JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "price", required = true) double price,
                       @JsonProperty("description") String description,
                       @JsonProperty("color") Color color) {
        cpt++;
        this.name = name;
        this.price = price;
        this.description = description;
        this.color = color;
    }

    // Methods

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

    public Color getColor() { return color; }

}
