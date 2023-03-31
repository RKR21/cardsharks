package com.estore.api.estoreapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an account.
 *
 * @author Adrian Marcellus
 */
public class Account {
    static final String STRING_FORMAT = "Account [userName=%s]";

    @JsonProperty("userName") private String userName;
    @JsonProperty("payments") private ArrayList<Payment> payments;

    final static int MAX_PAYMENTS = 3;

    /**
     * Constructor for Account
     * 
     * @param userName the account string username
     */
    public Account(@JsonProperty("userName") String userName){
        this.userName = userName;
        this.payments = new ArrayList<Payment>();
    }

    /**
     * gets the username field of the account
     * 
     * @return string username of the account
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * To String Object Override
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, userName);
    }

    /**
     * Static token generator function.
     * 
     * @param userName string used in generation
     * 
     * @return integer token value
     */
    public static int getToken(String userName){
        return userName.hashCode();
    }

    /**
     * Gets the payment array of max payment size
     * 
     * @return payment object array
     */
    public Payment[] getPayments(){
        return payments.toArray(new Payment[payments.size()]);
    }

    /**
     * Adds a payment method to an account.
     * 
     * @param payment Payment object toa dd
     * 
     * @return the added payment
     */
    public Payment addPayment(Payment payment){
        if(payments.size() <= MAX_PAYMENTS && !payments.contains(payment)){
            return null;
        }
        payments.add(payment);
        return payment;
    }

    /**
     * Removes a payment method from an account.
     * 
     * @param payment Payment object to remove
     * 
     * @return true if removed, false if not
     */
    public boolean removePayment(Payment payment){
        if(payments.remove(payment))
            return true;
        return false;
    }

}
