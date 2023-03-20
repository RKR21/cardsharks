package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an account.
 *
 * @author Adrian Marcellus
 */
public class Account {
    static final String STRING_FORMAT = "Account [userName=%s]";

    @JsonProperty("userName") private String userName;
    // TODO: Payment profile, owned inventory, password?

    /**
     * Constructor for Account
     * 
     * @param userName the account string username
     */
    public Account(@JsonProperty("userName") String userName){
        this.userName = userName;
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

}
