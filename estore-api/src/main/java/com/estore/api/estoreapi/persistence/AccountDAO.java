package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import com.estore.api.estoreapi.model.*;

/**
 * Defines methods for the AccountDAO api
 * 
 * @author Adrian Marcellus
*/

public interface AccountDAO {

    int logIn(String userName) throws IOException; //logOut?

    Account createAccount(Account account) throws IOException;

    boolean deleteAccount(String userName) throws IOException;
}
