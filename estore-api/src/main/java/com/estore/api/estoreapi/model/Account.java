package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import com.estore.api.estoreapi.model.Product;

/**
 * Represents an account.
 *
 * @author Adrian Marcellus
 */
public class Account {
    static final String STRING_FORMAT = "Account [userName=%s, cart=%d, inventory=%d]";

    @JsonProperty("userName") private String userName;
    @JsonProperty("cart") private ArrayList<Product> cart;
    @JsonProperty("inventory") private ArrayList<Product> inventory;

    public Account(@JsonProperty("userName") String userName, @JsonProperty("cart") ArrayList<Product> cart,
                   @JsonProperty("inventory") ArrayList<Product> inventory)
    {
        this.userName = userName;
        this.cart = cart;
        this.inventory = inventory;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public Product[] getCart(){
        return cart.toArray(new Product[cart.size()]);
    }

    public void addToCart(Product product){
        cart.add(product);
    }

    public void removeFromCart(int index){
        cart.remove(index);
    }

    public Product[] getInventory(){
        return inventory.toArray(new Product[inventory.size()]);
    }

    public void addToInventory(Product product){
        inventory.add(product);
    }

    public void removeFromInventory(int index){
        inventory.remove(index);
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, userName, cart, inventory);
    }

}
