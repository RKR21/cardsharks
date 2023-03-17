package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import com.estore.api.estoreapi.model.*;
/**
 * Defines methods for the CartDAO api
 * 
 * @author Adrian Marcellus
*/
public interface CartDAO {

    /**
     * Adds a new {@link Product product} to the database
     * 
     * @param product {@link Product product} object to be added 
     * id is ignored and handled seperatly to maintain consistincy.
     * 
     * @return new {@link Product product} object or null if unsuccessful
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Cart createCart(String userName) throws IOException;

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     * false if product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteCart(int token) throws IOException;

    /**
     * 
     * @param token
     * 
     * @param product
     * 
     * @return
     * 
     * @throws IOException
     */
    Product addToCart(int token, Product product) throws IOException;

    /**
     * 
     * @param token
     * 
     * @param index
     * 
     * @return
     * 
     * @throws IOException
     */
    boolean removeFromCart(int token, int index) throws IOException;

    /**
     * 
     * @param token
     * 
     * @return
     * 
     * @throws IOException
     */
    Product[] getCart(int token) throws IOException;

}

