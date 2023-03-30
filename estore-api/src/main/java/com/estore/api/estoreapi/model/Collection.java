package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * Represents a collection.
 *
 * @author Adrian Marcellus
 */
public class Collection {
    static final String STRING_FORMAT = "Collection [userName=%s]";

    @JsonProperty("userName") private String userName;
    @JsonProperty("collection") private ArrayList<Product> collection;

    /**
     * Contstructor for a collection object
     * 
     * @param userName string username of the account 
     */
    public Collection(@JsonProperty("userName") String userName)
    {
        this.userName = userName;
        this.collection = new ArrayList<Product>();
    }

    /**
     * Get the collections product array
     * 
     * @return product array 
     */
    public Product[] getCollection(){
        return collection.toArray(new Product[collection.size()]);
    }

    /**
     * Gets the account token connected to the collection
     * 
     * @return int token
     */
    public int getToken(){
        return Account.getToken(userName);
    }

    /**
     * Adds a product to the collection
     * 
     * @param product product object to add
     * 
     * @return the product that was added or updated
     */
    public Product addToCollection(Product product){
        for(Product item : collection){
            if(item.getId() == product.getId()){
                item.setQuantity(item.getQuantity()+1);
                return item;
            }
        }
        product.setQuantity(1);
        collection.add(product);
        return product;
    }

    /**
     * Removes a product from the collection by one quantity if 
     * quanity is zero removes the product from the collection.
     * 
     * @param id product id to remove
     * 
     * @return true if removed sucessfully false otherwsie
     */
    public boolean removeFromCollection(int id){
        for(Product product : collection)
            if(product.getId() == id){
                int quantity = product.getQuantity() - 1;
                product.setQuantity(quantity);
                if(quantity <= 0)
                    collection.remove(product);
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

    /**
     * Checks if a collection contains a product
     * 
     * @param product product to check collection for
     * 
     * @return true if exists false otherwise
     */
    public boolean contains(Product product){
        return this.collection.contains(product);
    }
}
