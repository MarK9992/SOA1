package fr.unice.polytech.soa1.volley.accounts;

import fr.unice.polytech.soa1.volley.catalog.VolleyStuff;

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
    private Map<VolleyStuff, Integer> basket;

    // Constructors

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
        this.address = "";
        this.basket = new HashMap<VolleyStuff, Integer>();
    }

    // Methods

    // Getters and setters

    public String getLogin() {
        return login;
    }
}
