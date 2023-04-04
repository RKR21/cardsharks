package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Cart class
 * 
 * @author Adrian Marcellus
 */
@Tag("Model-tier")
public class CartTest {
    private Cart cart;
    private Product testProduct = new Product
        (11,"Charmander", 2.50, 15);

    // Creates a test product before each test
    @BeforeEach
    public void setupCart () {
        // Constructor Parameters
        String userName = "test";
        this.cart = new Cart(userName);
    }

    // Tests Cart constructor to ensure proper object creation
    @Test
    public void testCart() {
        // Setup
        String expected_name  = "test";
        Product[] expected_cart = {};
        // Analysis
        assertEquals(Account.getToken(expected_name), cart.getToken());
        assertEquals(expected_cart.length, cart.getCart().length);
    }

    // Tests adding products
    @Test
    public void testAddToCart() {
        //Invoke and Analysis
        assertNotNull(cart.addToCart(testProduct));
        assertEquals(testProduct, cart.getCart()[0]);
        assert(cart.addToCart(testProduct).getQuantity() == 2);
    }

    // Tests removing products
    @Test
    public void testRemoveFromCart() {
        //Setup
        cart.addToCart(testProduct);
        //Invoke and analysis
        assert(cart.removeFromCart(testProduct.getId()));
        assert(!cart.removeFromCart(testProduct.getId()));
        cart.addToCart(testProduct);
        cart.addToCart(testProduct);
        cart.removeFromCart(testProduct.getId());
        assert(cart.getCart()[0].getQuantity() == 1);
    }

    // Tests toString () method
    @Test
    public void testToString () {
        // Setup
        String userName = "test";

        // Invoke
        String expected_toString = String.format(Cart.STRING_FORMAT, userName);

        // Analysis
        assertEquals(expected_toString, cart.toString());
    }
}