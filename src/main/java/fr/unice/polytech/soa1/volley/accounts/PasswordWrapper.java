package fr.unice.polytech.soa1.volley.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marc Karassev
 *
 * Utiliy class used to wrap a JSON object representing a password.
 * A PasswordWrapper has the following JSON representation:
 *  {"value":"password_value"}
 */
public class PasswordWrapper {

    // Attribute

    private String value;

    // Constructors

    @JsonCreator
    public PasswordWrapper(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Getters and setters

    public String getValue() {
        return value;
    }
}
