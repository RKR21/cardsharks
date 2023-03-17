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
    // TODO: Payment profile, inventory, password?

    /**
     * 
     * @param userName
     */
    public Account(@JsonProperty("userName") String userName){
        this.userName = userName;
    }

    /**
     * 
     * @return
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
     * 
     * @param userName
     * @return
     */
    public static int getToken(String userName){
        return userName.hashCode();
    }

}
