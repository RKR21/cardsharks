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
     * Adds a new {@link Cart cart} to the database
     * 
     * @param userName string user name to create cart for
     * 
     * @return new {@link Cart cart} object or null if unsuccessful
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Cart createCart(String userName) throws IOException;

    /**
     * Deletes a {@linkplain Cart cart} with the given username
     * 
     * @param token The token to find and authenticate for the {@link Cart cart}
     * 
     * @return true if the {@link Cart cart} was deleted
     * false if cart with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteCart(int token) throws IOException;

    /**
     * Adds a {@linkplain Product product} to {@link Cart cart} 
     * 
     * @param token int token value used to authenticate request and locate cart
     * 
     * @param product {@linkplain Product product} to add
     * 
     * @return the {@linkplain Product product} that was added
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product addToCart(int token, Product product) throws IOException;

    /**
     * Removes a {@linkplain Product product} to {@link Cart cart} 
     * 
     * @param token int token value used to authenticate request and locate cart
     * 
     * @param id remove a {@linkplain Product product} by its id
     * 
     * @return the true is successfully removed, false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean removeFromCart(int token, int id) throws IOException;

    /**
     * Gets the {@linkplain Product product} array from the {@link Cart cart} 
     * 
     * @param token int token value used to authenticate request and locate cart
     * 
     * @return {@linkplain Product product} array
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product[] getCart(int token) throws IOException;

}

