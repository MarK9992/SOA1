package fr.unice.polytech.soa1.volley.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marc Karassev
 */
public class Account {

    // Attributes

    private String login;
    private String password;
    private String address;
    private Map<String, Integer> basket;

    // Constructors

    @JsonCreator
    public Account(@JsonProperty(value = "login", required = true) String login,
                   @JsonProperty(value = "password", required = true) String password,
                   @JsonProperty("address") String address) {
        this.login = login;
        this.password = password;
        this.address = address;
        this.basket = new HashMap<String, Integer>();
    }

    // Methods

    // Getters and setters

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public Map<String, Integer> getBasket() {
        return basket;
    }
}
