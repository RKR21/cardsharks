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
     * Gets the appropriate {@link Token token} for a {@link Account account}
     * 
     * @param userName string user name trying to log in 
     * 
     * @return A token with a value of the found account
     * or 0 for an account that doesn't exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Token logIn(String userName) throws IOException;

    /**
     * Adds a new {@link Account account} to the database
     * 
     * @param account {@link Account account} object to be added 
     * id is ignored and handled seperatly to maintain consistincy.
     * 
     * @return new {@link Account account} object or null if unsuccessful
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Account createAccount(Account account) throws IOException;

    /**
     * Deletes a {@linkplain Account account} with the given username
     * 
     * @param username The username of the {@link Account account}
     * 
     * @return true if the {@link Account account} was deleted
     * false if account with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteAccount(String userName) throws IOException;
}
