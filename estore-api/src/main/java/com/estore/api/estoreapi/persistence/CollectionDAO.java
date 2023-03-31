package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import com.estore.api.estoreapi.model.*;
/**
 * Defines methods for the CollectionDAO api
 * 
 * @author Adrian Marcellus
*/
public interface CollectionDAO {

    /**
     * Adds a new {@link Collection collection} to the database
     * 
     * @param userName userName string user name to create cart for
     * 
     * @return new {@link Collection collection} object or null if unsuccessful
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Collection createCollection(String userName) throws IOException;

    /**
     * Deletes a {@linkplain Collection collection} with the given username
     * 
     * @param token The token to find and authenticate for the {@link Collection collection}
     * 
     * @return true if the {@link Collection collection} was deleted
     * false if collection with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteCollection(int token) throws IOException;

    /**
     * Adds a {@linkplain Product product} to {@link Collection collection} 
     * 
     * @param token int token value used to authenticate request and locate collection
     * 
     * @param product {@linkplain Product product} to add
     * 
     * @return the {@linkplain Product product} that was added
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product addToCollection(int token, Product product) throws IOException;

    /**
     * Removes a {@linkplain Product product} to {@link Collection collection} 
     * 
     * @param token int token value used to authenticate request and locate cart
     * 
     * @param id remove a {@linkplain Product product} by its id
     * 
     * @return the true is successfully removed, false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean removeFromCollection(int token, int id) throws IOException;

    /**
     * Gets the {@linkplain Product product} array from the {@link Collection collection} 
     * 
     * @param token int token value used to authenticate request and locate collection
     * 
     * @return {@linkplain Product product} array
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product[] getCollection(int token) throws IOException;



    /**<---------TRADE FUNCTIONALITY-------------------------->*/
    /**
     * Makes a outgoing trade offer
     * 
     * @param token used to  authenticate offer
     * @param userName
     * @param otherName
     * @param request
     * @param offer
     * 
     * @return outgoing {@linkplain Trade trade} object
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Trade makeOffer(int token, 
        String userName, String otherName, 
        Product request, Product offer)throws IOException;

    /**
     * Accepts a pending offer if it exists under a token
     * 
     * @param token token used to find and authenticate offer
     * 
     * @return true if accepted successfully false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean acceptOffer(int token)throws IOException;

    /**
     * Rejects a pending offer if it exists under a token
     * 
     * @param token token used to find and authenticate offer
     * 
     * @return true if rejected successfully false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean rejectOffer(int token)throws IOException;

    /**
     * Gets a pending offer if it exists under a token
     * 
     * @param token token used to find and authenticate offer
     * 
     * @return pending {@linkplain Trade trade} object or null if non-existant
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Trade getOffer(int token)throws IOException;
}

