package fr.unice.polytech.soa1.volley.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.soa1.volley.basket.BasketItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marc Karassev
 */
public class Account {

    // Attributes

    private String login;
    private String password;
    private String address;
    private List<BasketItem> basket;

    // Constructors

    @JsonCreator
    public Account(@JsonProperty(value = "login", required = true) String login,
                   @JsonProperty(value = "password", required = true) String password,
                   @JsonProperty("address") String address) {
        this.login = login;
        this.password = password;
        this.address = address;
        this.basket = new ArrayList<BasketItem>();
    }

    // Methods

    public void emptyBasket(){
        this.basket.clear();
    }

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

    public List<BasketItem> getBasket() {
        return basket;
    }
}
