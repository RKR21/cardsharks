package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Represents a cart.
 *
 * @author Adrian Marcellus
 */
public class Cart {
    static final String STRING_FORMAT = "Cart[token=%d, cart=%d]";

    @JsonProperty("token") private int token;
    @JsonProperty("cart") private ArrayList<Product> cart;

    public Cart(@JsonProperty("token") int token)
    {
        this.token = token;
        this.cart = new ArrayList<Product>();
    }

    public Product[] getCart(){
        return cart.toArray(new Product[cart.size()]);
    }

    public int getToken(){
        return token;
    }

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

    public boolean removeFromCart(int index){
        if(index < cart.size() && cart.remove(index) != null)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, token, cart);
    }

}
