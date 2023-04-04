package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a payment.
 *
 * @author Adrian Marcellus
 */
public class Payment {
    static final String STRING_FORMAT = "Payment [type=%s]";

    @JsonProperty("type") private String type;

    /**
     * Constructor for Payment
     * 
     * @param type the payment string type
     */
    public Payment(@JsonProperty("type") String type){
        this.type = type;
    }

    /**
     * gets the type field of the payment
     * 
     * @return string type of the payment
     */
    public String getType(){
        return this.type;
    }

    /**
     * Checks if a payment is equal to another,
     * object equals override.
     * 
     * @return true if equal false otherwise
     */
    @Override
    public boolean equals(Object object){
        if(object != null && object instanceof Payment)
            return this.type.equals(((Payment) object).getType());
        return false;
    }

    /**
     * Overrides hashcode to make SonarQube happy
     * 
     * @return int hashcode for product
     */
    @Override
    public int hashCode(){
        return this.type.hashCode();
    }

    /**
     * To String Object Override
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, type);
    }
}