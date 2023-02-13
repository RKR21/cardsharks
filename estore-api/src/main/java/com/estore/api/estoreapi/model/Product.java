package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    
    static final String STRING_FORMAT = "Product [id=%d, name=%s, price=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;

    
    /**
     * Create a new Product object
     * 
     * 
     * 
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, 
        @JsonProperty("price") double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public double getPrice(){
        return this.price;
    }
    public void setPrice(double price){
        this.price = price;
    }



    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,price);
    }
    
}
