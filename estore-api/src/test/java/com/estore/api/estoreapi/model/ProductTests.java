package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Product class
 * 
 * @author Group Member
 * @author Joshua Matthews
 */
@Tag("Model-tier")
public class ProductTests {
    private Product product;

    // Creates a test product before each test
    @BeforeEach
    public void setupProduct () {
        // Constructor Parameters
        int id = 10;
        String name  = "TestName";
        double price = 13.5f;
        int quantity = 15;

        // Assigning a product object to the private product variable
        product = new Product(id, name, price, quantity);
    }

    @Test
    public void testProduct () {

        // Setup
        int expected_id = 10;
        String expected_name  = "TestName";
        double expected_price = 13.5f;
        int expected_quantity = 15;

        // Invoke
        Product product1 = new Product(expected_id, expected_name, expected_price, expected_quantity);

        // Analysis
        assertEquals(expected_id, product1.getId());
        assertEquals(expected_name, product1.getName());
        assertEquals(expected_price, product1.getPrice());
        assertEquals(expected_quantity, product1.getQuantity());
    }
    
    @Test
    public void testGetID () {
        // Setup
        int id_id = 10;
        int notid = 25;

        // Invoke
        int id = product.getId();

        // Analysis
        assertEquals(id, id_id);
        assertNotEquals(id, notid);
    }

    @Test
    public void testGetName () {
        // Setup
        String name = "TestName";
        String notName = "Josh";

        // Invoke
        String n = product.getName();

        // Analysis
        assertEquals(n, name);
        assertNotEquals(n, notName);
    }

    @Test
    public void testSetName () {
        // Setup
        String newName = "Testname2";
        String name = "TestName";

        // Invoke
        product.setName(newName);

        // Analysis
        assertEquals(newName, product.getName());
        assertNotEquals(name, product.getName());
    }

    @Test
    public void testGetPrice () {
        // Setup
        double price_price = 13.5f;
        double notprice = 25f;

        // Invoke
        double p = product.getPrice();

        // Analysis
        assertEquals(p, price_price);
        assertNotEquals(p, notprice);
    }

    @Test
    public void testSetPrice () {
        
        double new_price = 50f;
        product.setPrice(new_price);
        assertEquals(new_price, product.getPrice());
    }

    @Test
    public void testQuantity () {

        int new_quantity = 500;
        product.setQuantity(new_quantity);
        assertEquals(new_quantity, product.getQuantity());
    }

    @Test
    public void testToString () {
        int id = 10;
        String name  = "TestName";
        double price = 13.5f;
        int quantity = 15;

        String expected_toString = String.format(Product.STRING_FORMAT, id, name, price, quantity);

        assertEquals(expected_toString, product.toString());

    }
}
