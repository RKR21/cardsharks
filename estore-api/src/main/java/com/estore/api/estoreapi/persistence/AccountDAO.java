package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import com.estore.api.estoreapi.model.*;

/**
 * Defines methods for the AccountDAO api
 * 
 * @author Adrian Marcellus
*/

public interface AccountDAO {

    /**
     * 
     * 
     * @param userName
     * 
     * @return
     * 
     * @throws IOException
     */
    int logIn(String userName) throws IOException;

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
    Account createAccount(Account account) throws IOException;

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
    boolean deleteAccount(String userName) throws IOException;
}
