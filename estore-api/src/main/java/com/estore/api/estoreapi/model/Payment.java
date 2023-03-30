package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
     * Checks if a payment is equal to each another.
     * 
     * @return boolean object equals override
     */
    @Override
    public boolean equals(Object object){
        if(object != null && object instanceof Payment)
            return this.type.equals(((Payment) object).getType());
        return false;
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