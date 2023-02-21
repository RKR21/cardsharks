package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

/**
 * Defines methods for the InventoryDAO api
 * 
 * @author Elijah Lenhard
 * @author Adrian Marcellus
*/

public interface InventoryDAO {
    
    /**
     * Retrives an array of all {@link Product products}
     * 
     * @return an array of {@link Product product} objects
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product[] getProducts() throws IOException;

    /**
     * Retrives the {@link Product product} with the given id
     * 
     * @param id id of the {@link Product product} to be returned
     * 
     * @return the {@link Product product} object with the id given
     * null if no {@link Product product} with the given id exists
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product getProduct(int id) throws IOException;

    /**
     * Retrives an array of all {@link Product products} that contain a substring
     * 
     * @param String substring to search for
     * 
     * @return an array of {@link Product product} objects that contain the substring in their display name
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product[] findProducts(String subString) throws IOException;

    /**
     * updates an existing {@link Product product} 
     * 
     * @param product new {@link Product product} object
     * 
     * @return the {@link Product product} object that was updated
     * or null if no object was found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

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
    boolean deleteProduct(int id) throws IOException;
    
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
    Product createProduct(Product product) throws IOException;
}
