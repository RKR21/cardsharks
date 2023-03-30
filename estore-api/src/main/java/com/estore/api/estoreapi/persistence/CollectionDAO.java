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
     * @param collection {@link Collection collection} object to be added 
     * id is ignored and handled seperatly to maintain consistincy.
     * 
     * @return new {@link Collection collection} object or null if unsuccessful
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Collection createCollection(String userName) throws IOException;

    /**
     * Deletes a {@linkplain Collection collection} with the given username
     * 
     * @param username The username of the {@link Collection collection}
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
     * @param token int token value used to authenticate request and locate collection
     * 
     * @param index array location to remove a {@linkplain Product product}
     * 
     * @return the true is successfully removed, false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean removeFromCollection(int token, int index) throws IOException;

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
     * 
     * @param token
     * @param userName
     * @param otherName
     * @param request
     * @param offer
     * 
     * @return
     * 
     * @throws IOException
     */
    Trade makeOffer(int token, 
        String userName, String otherName, 
        Product request, Product offer)throws IOException;

    /**
     * 
     * @param token
     * 
     * @return
     * 
     * @throws IOException
     */
    boolean acceptOffer(int token)throws IOException;

    /**
     * 
     * @param token
     * 
     * @return
     * 
     * @throws IOException
     */
    boolean rejectOffer(int token)throws IOException;

    /**
     * 
     * @param token
     * 
     * @return
     * 
     * @throws IOException
     */
    Trade getOffer(int token)throws IOException;
}

