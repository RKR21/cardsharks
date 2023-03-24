package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Represents a cart.
 *
 * @author Adrian Marcellus
 */
public class Cart {
    static final String STRING_FORMAT = "Cart [userName=%s]";

    @JsonProperty("userName") private String userName;
    @JsonProperty("cart") private ArrayList<Product> cart;

    /**
     * Contstructor for a cart object
     * 
     * @param userName string username of the account 
     */
    public Cart(@JsonProperty("userName") String userName)
    {
        this.userName = userName;
        this.cart = new ArrayList<Product>();
    }

    /**
     * Get the carts product array
     * 
     * @return product array 
     */
    public Product[] getCart(){
        return cart.toArray(new Product[cart.size()]);
    }

    /**
     * Gets the account token connected to the cart
     * 
     * @return int token
     */
    public int getToken(){
        return Account.getToken(userName);
    }

    /**
     * Adds a product to the cart
     * 
     * @param product product object to add
     * 
     * @return the product that was added or updated
     */
    public Product addToCart(Product product){
        for(Product item : cart){
            if(item.getId() == product.getId()){
                item.setQuantity(item.getQuantity()+1);
                return item;
            }
        }
        product.setQuantity(1);
        cart.add(product);
        return product;
    }

    /**
     * Removes a product from the cart.
     * 
     * @param id product id to remove
     * 
     * @return true if removed sucessfully false otherwsie
     */
    public boolean removeFromCart(int id){
        for(Product product : cart)
            if(product.getId() == id){
                int quantity = product.getQuantity() - 1;
                product.setQuantity(quantity);
                if(quantity <= 0)
                    cart.remove(product);
                return true;
            }
        return false;
    }

    /**
     * To String Object Override
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, userName);
    }

}
