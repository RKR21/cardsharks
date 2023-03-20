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
     * Generates an array of {@linkplain Cart carts} from the tree map
     * 
     * @return  The array of {@link Cart carts}, may be empty
     */
    private Cart[] getCartArray() {
        return carts.values().toArray(new Cart[carts.size()]);
    }

    /**
     * Saves the {@linkplain Cart carts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Cart carts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Cart[] cartArray = getCartArray();
        objectMapper.writeValue(new File(filename), cartArray);
        return true;
    }

    /**
     * Loads {@linkplain Cart carts} from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        carts = new HashMap<>();
        Cart[] cartArray = objectMapper.readValue(new File(filename), Cart[].class);
        for (Cart cart : cartArray)
        carts.put(cart.getToken(), cart);
        return true;
    }

    /**
     * CartDAO Override: creates a cart.
     */
    @Override
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
     * CartDAO Override: deletes a cart.
     */
    @Override
    public boolean deleteCart(int token) throws IOException{
        if(carts.remove(token) ==  null)
            return false;
        return save(); 
    }

    /**
     * CartDAO Override: adds to cart.
     */
    @Override
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
     * CartDAO Override: removes from cart.
     */
    @Override
    public boolean removeFromCart(int token, int index) throws IOException{
        synchronized(carts){
            if(!carts.containsKey(token))
                return false;
            return carts.get(token).removeFromCart(index) && save();
        }
    }

    /**
     * CartDAO Override: gets cart product array
     */
    @Override
    public Product[] getCart(int token) throws IOException{
        synchronized(carts){
            if(!carts.containsKey(token))
                return null;
            return carts.get(token).getCart();
        }
    }
}
