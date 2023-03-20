package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Token.
 *
 * @author Adrian Marcellus
 */
public class Token {
    static final String STRING_FORMAT = "Token [token=%d]";

    @JsonProperty("token") private int token;

    /**
     * Constructor for a token object.
     * 
     * @param token value of the token
     */
    public Token(@JsonProperty("token") int token){
        this.token = token;
    }

    /**
     * Gets the token value
     * 
     * @return int token value
     */
    public int getToken(){
        return this.token;
    }

    /**
     * To String Object Override
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, token);
    }
}
