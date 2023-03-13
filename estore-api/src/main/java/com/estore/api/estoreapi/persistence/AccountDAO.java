package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;

import com.estore.api.estoreapi.model.*;

/**
 * Defines methods for the AccountDAO api
 * 
 * @author Adrian Marcellus
*/

public interface AccountDAO {

    Account createAccount(Account account) throws IOException;

    boolean deleteAccount(String userName) throws IOException;

    Product addToCart(String userName, Product product) throws IOException;

    boolean removeFromCart(String userName, int index) throws IOException;

    Product[] getCart(String userName) throws IOException;

    Product addToInventory(String userName, Product product) throws IOException;

    boolean removeFromInventory(String userName, int index) throws IOException;

    Product[] getInventory(String userName) throws IOException;

}
