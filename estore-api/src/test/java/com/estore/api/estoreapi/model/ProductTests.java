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

    // Tests product constructor to ensure proper object creation
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
    
    // Tests getID () method
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

    // Tests getName () method
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

    // Tests setName () method
    @Test
    public void testSetName () {
        // Setup
        String name = "TestName";
        String newName = "Testname2";

        // Invoke
        product.setName(newName);

        // Analysis
        assertEquals(newName, product.getName());
        assertNotEquals(name, product.getName());
    }

    // Tests getPrice () method
    @Test
    public void testGetPrice () {
        // Setup
        double price = 13.5f;
        double notprice = 25f;

        // Invoke
        double p = product.getPrice();

        // Analysis
        assertEquals(p, price);
        assertNotEquals(p, notprice);
    }

    // Tests setPrice () method
    @Test
    public void testSetPrice () {
        // Setup
        double price = 13.5f;
        double new_price = 50f;

        // Invoke
        product.setPrice(new_price);

        // Analysis
        assertEquals(new_price, product.getPrice());
        assertNotEquals(price, product.getPrice());
    }

    // Tests getQuantity () method
    @Test
    public void testGetQuantity () {
        // Setup
        int quantity = 15;
        int notquantity = 25;

        // Invoke
        int q = product.getQuantity();

        // Analysis
        assertEquals(q, quantity);
        assertNotEquals(q, notquantity);
    }

    // Tests setQuantity () method
    @Test
    public void testSetQuantity () {

        int new_quantity = 500;
        product.setQuantity(new_quantity);
        assertEquals(new_quantity, product.getQuantity());
    }

    // Tests toString () method
    @Test
    public void testToString () {
        // Setup
        int id = 10;
        String name  = "TestName";
        double price = 13.5f;
        int quantity = 15;

        // Invoke
        String expected_toString = String.format(Product.STRING_FORMAT, id, name, price, quantity);

        // Analysis
        assertEquals(expected_toString, product.toString());
        assertNotEquals("", product.toString());
    }
}
