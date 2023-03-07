package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ProductTests {

    @Test
    public void testConstructor(){

        int expected_id = 10;
        String expected_name  = "TestName";
        double expected_price = 13.5f;
        int expected_quantity = 15;

        Product product = new Product(expected_id, expected_name, expected_price, expected_quantity);

        assertEquals(expected_id, product.getId());
        assertEquals(expected_name, product.getName());
        assertEquals(expected_price, product.getPrice());
        assertEquals(expected_quantity, product.getQuantity());
    }
    
    @Test
    public void testName(){
        int id = 10;
        String name  = "TestName";
        double price = 13.5f;
        int quantity = 15;

        Product product = new Product(id, name, price, quantity);

        String newName = "Testname2";
        product.setName(newName);

        assertEquals(newName, product.getName());
    }

    @Test
    public void testPrice(){
        int id = 10;
        String name  = "TestName";
        double price = 13.5f;
        int quantity = 15;

        Product product = new Product(id, name, price, quantity);
        
        double new_price = 50f;
        product.setPrice(new_price);
        assertEquals(new_price, product.getPrice());
    }

    @Test
    public void testQuantity(){
        int id = 10;
        String name  = "TestName";
        double price = 13.5f;
        int quantity = 15;

        Product product = new Product(id, name, price, quantity);

        int new_quantity = 500;
        product.setQuantity(new_quantity);
        assertEquals(new_quantity, product.getQuantity());
    }

    @Test
    public void testToString(){
        int id = 10;
        String name  = "TestName";
        double price = 13.5f;
        int quantity = 15;

        Product product = new Product(id, name, price, quantity);

        String expected_toString = String.format(Product.STRING_FORMAT, id, name, price, quantity);

        assertEquals(expected_toString, product.toString());

    }
}
