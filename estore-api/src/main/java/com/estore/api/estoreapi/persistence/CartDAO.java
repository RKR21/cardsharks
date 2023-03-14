package com.estore.api.estoreapi.persistence;
import java.io.IOException;

import com.estore.api.estoreapi.model.*;
/**
 * Defines methods for the CartDAO api
 * 
 * @author Adrian Marcellus
*/
public interface CartDAO {

    Cart createCart(String userName) throws IOException;

    boolean deleteCart(int token) throws IOException;

    Product addToCart(int token, Product product) throws IOException;

    boolean removeFromCart(int token, int index) throws IOException;

    Product[] getCart(int token) throws IOException;

}

