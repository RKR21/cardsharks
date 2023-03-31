package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a  trade offer.
 *
 * @author Adrian Marcellus
 */
public class Trade {
    static final String STRING_FORMAT = "Trade [fromUser=%s, toUser=%s]";

    @JsonProperty("fromUser") private String fromUser;
    @JsonProperty("toUser") private String toUser;
    @JsonProperty("offer") private Product offer;
    @JsonProperty("request") private Product request;

    /**
     * Constructor for a token object.
     * 
     * @param token value of the token
     */
    public Trade(@JsonProperty("fromUser") String fromUser, @JsonProperty("toUser") String toUser,
        @JsonProperty("offer") Product offer,@JsonProperty("request") Product request)
    {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.offer = offer;
        this.request = request;
    }

    /**
     * string name of user offering trade
     * 
     * @return fromUser String
     */
    public String getFromUser(){ return fromUser;}

    /**
     * string name of user being offered trade
     * 
     * @return toUser String
     */
    public String getToUser(){ return toUser;}

    /**
     * product bring offered
     * 
     * @return offer Product
     */
    public Product getOffer(){ return offer;}

    /**
     * product being requested
     * 
     * @return request Product
     */
    public Product getRequest(){ return request;}

    /**
     * To String Object Override
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, fromUser, toUser, offer, request);
    }
}
