package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a product
 * 
 * 
 * @author Elijah Lenhard
 */
public class Product {
    static final String STRING_FORMAT = "Product [id=%d, name=%s, price=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    
    /**
     * Create a new Product object
     * @Param id Unique int for the product
     * @Param name Display name of the product
     * @Param price Listed price of the product
     * 
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, 
        @JsonProperty("price") double price, @JsonProperty("quantity") int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Retrives the id of the product
     * @return Id of the product
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retrives the name of the product
     * @return name of product
     */
    public String getName(){
        return this.name;
    }

    /**
     * Updates the name of the product
     * @param String new name for product
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Retrives the price of the product
     * @return price of the product
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Updates the price of the product
     * @param double new price for product
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * gets the quantity of a product in the inventory
     * @return int quantity
     */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * sets the quantity of a product in the inventory
     * @param quantity amount to set the quantity to
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price);
    }
    
}
