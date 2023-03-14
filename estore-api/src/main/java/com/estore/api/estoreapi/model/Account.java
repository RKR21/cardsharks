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

    public Account(@JsonProperty("userName") String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, userName);
    }

    public static int getToken(String userName){
        return userName.hashCode();
    }

}
