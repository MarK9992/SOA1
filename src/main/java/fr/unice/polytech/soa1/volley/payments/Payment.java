package fr.unice.polytech.soa1.volley.payments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {

    // Attributes

    private String id;
    private String customer;
    private String address;
    private double amount;
    private String orderReference;
    private long cardNumber;
    private short cryptogram;
    private String validityDate;

    
    // Constructors

    @JsonCreator
    public Payment(@JsonProperty(value = "customer") String customer,
                   @JsonProperty(value = "address", required = true) String address,
                   @JsonProperty(value = "amount") double amount,
                   @JsonProperty(value = "orderRef") String orderRef,
                   @JsonProperty(value = "cardNumber", required = true) long cardNumber,
                   @JsonProperty(value = "crypto", required = true) short cryptogram,
                   @JsonProperty(value = "validityDate", required = true) String date){
        this.id = orderRef;
        this.customer = customer;
        this.address = address;
        this.amount = amount;
        this.orderReference = orderRef;
        this.cardNumber = cardNumber;
        this.cryptogram = cryptogram;
        this.validityDate = date;
    }

    // Methods

    @Override
    public String toString() {
        return "{ " + id + ", " + customer + ", " + address+ ", " + amount + ", " + orderReference + ", " + cardNumber + " }";
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public double getAmount() {
        return amount;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    @JsonIgnore
    public short getCryptogram() {
        return cryptogram;
    }

    @JsonIgnore
    public String getValidityDate() {
        return validityDate;
    }
}
