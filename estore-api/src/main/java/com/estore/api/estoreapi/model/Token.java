package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Token.
 *
 * @author Adrian Marcellus
 */
public class Token {
    static final String STRING_FORMAT = "Token [userName=%d]";

    @JsonProperty("token") private int token;

    public Token(@JsonProperty("token") int token){
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, token);
    }
}
