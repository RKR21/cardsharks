package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Represents a cart.
 *
 * @author Adrian Marcellus
 */
public class Cart {
    static final String STRING_FORMAT = "Cart [userName=%s, cart=%d]";

    @JsonProperty("userName") private String userName;
    @JsonProperty("cart") private ArrayList<Product> cart;

    /**
     * 
     * @param userName
     */
    public Cart(@JsonProperty("userName") String userName)
    {
        this.cart = new ArrayList<Product>();
    }

    /**
     * 
     * @return
     */
    public Product[] getCart(){
        return cart.toArray(new Product[cart.size()]);
    }

    /**
     * 
     * @return
     */
    public int getToken(){
        return Account.getToken(userName);
    }

    /**
     * 
     * @param product
     * @return
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
     * 
     * @param index
     * @return
     */
    public boolean removeFromCart(int index){
        if(index < cart.size() && cart.remove(index) != null)
            return true;
        return false;
    }

    /**
     * To String Object Override
     * 
     * @return String
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, userName, cart);
    }

}
