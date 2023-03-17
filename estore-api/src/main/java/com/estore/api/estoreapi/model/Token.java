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
     * 
     * @param token
     */
    public Token(@JsonProperty("token") int token){
        this.token = token;
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
