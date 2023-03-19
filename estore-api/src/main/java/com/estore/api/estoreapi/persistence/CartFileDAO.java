package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.*;

/**
 * Implements the functionality for JSON file-based peristance for Products
 *
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author SWEN Faculty
 * @author Adrian Marcellus
 */
@Component
public class CartFileDAO implements CartDAO{
    private static final Logger LOG = Logger.getLogger(CartFileDAO.class.getName());
    Map<Integer, Cart> carts;
    private ObjectMapper objectMapper;
    private String filename;

    /**
     * Creates a Cart File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CartFileDAO(@Value("${carts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * 
     * @return
     */
    private Cart[] getCartArray() {
        return carts.values().toArray(new Cart[carts.size()]);
    }

    /**
     * 
     * @return
     * 
     * @throws IOException
     */
    private boolean save() throws IOException {
        Cart[] cartArray = getCartArray();
        objectMapper.writeValue(new File(filename), cartArray);
        return true;
    }

    /**
     * 
     * @return
     * 
     * @throws IOException
     */
    private boolean load() throws IOException {
        carts = new HashMap<>();
        Cart[] cartArray = objectMapper.readValue(new File(filename), Cart[].class);
        for (Cart cart : cartArray)
        carts.put(cart.getToken(), cart);
        return true;
    }

    /**
     * 
     */
    public Cart createCart(String userName) throws IOException{
        int token = Account.getToken(userName);
        if(carts.containsKey(token))
            return null;
        Cart newCart = new Cart(userName);
        carts.put(token, newCart);
        save();
        return newCart; 
    }

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
    public boolean deleteCart(int token) throws IOException{
        if(carts.remove(token) ==  null)
            return false;
        return save(); 
    }

    /**
     * 
     */
    public Product addToCart(int token, Product product) throws IOException{
        synchronized(carts){
            if(!carts.containsKey(token))
                return null;
            carts.get(token).addToCart(product);
            save();
            return product;
        }
    }

    /**
     * 
     */
    public boolean removeFromCart(int token, int index) throws IOException{
        synchronized(carts){
            if(!carts.containsKey(token))
                return false;
                carts.get(token).removeFromCart(index);
            return save();
        }
    }
    /**
     * 
     */
    public Product[] getCart(int token) throws IOException{
        synchronized(carts){
            if(!carts.containsKey(token))
                return null;
            return carts.get(token).getCart();
        }
    }
}