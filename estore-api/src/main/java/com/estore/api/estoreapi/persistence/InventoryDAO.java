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
     * @return an array of {@link Product product} objects
     * @throws IOException 
     */
    Product[] getProducts() throws IOException;

    /**
     * Retrives the {@link Product product} with the given id
     * @param id id of the {@link Product product} to be returned
     * @return the {@link Product product} object with the id given
     * <br>
     * null if no {@link Product product} with the given id exists
     * @throws IOException
     */
    Product getProduct(int id) throws IOException;

    /**
     * Retrives an array of all {@link Product products} that contain a substring
     * @param String substring to search for
     * @return an array of {@link Product product} objects that contain the substring in their display name
     * @throws IOException 
     */
    Product[] findProducts(String subString) throws IOException;

    /**
     * updates an existing {@link Product product} 
     * @param product new {@link Product product} object
     * @return the {@link Product product} object that was updated
     * <br>
     * or null if no object was found
     * 
     * @throws IOException
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * deletes a {@link Product product} from the database
     * @param id id of the {@link Product product} to be deleted
     * @return the {@link Product product} that was deleted
     * <br>
     * or null if the {@link Product product} was not found
     * @throws IOException
     */
    boolean deleteProduct(int id) throws IOException;
    /**
     * Adds a new {@link Product product} to the database
     * @param product {@link Product product} object to be added 
     * <br>
     * id is ignored and handled seperatly to maintain consistincy.
     * @return new {@link Product product} object or null if unsuccessful
     * @throws IOException
     */
    Product createProduct(Product product) throws IOException;
}
