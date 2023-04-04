package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Trade class
 * 
 * @author Adrian Marcellus
 */
@Tag("Model-tier")
public class TradeTest {
    private Trade trade;

    // Creates a test trade before each test
    @BeforeEach
    public void setupTrade () {
        // Constructor Parameters
        String fromUser = "fromUser";
        String toUser = "toUser";
        Product offer = new Product(11,"Charmander", 2.50, 15);
        Product request = new Product(12,"Charmeleon", 5.00, 10);
        // Assigning a trade object to the private trade variable
        trade = new Trade(fromUser, toUser, offer, request);
    }

    // Tests trade constructor to ensure proper object creation
    @Test
    public void testTrade () {
        // Setup
        String eFromUser = "fromUser";
        String eToUser = "toUser";
        Product eOffer = new Product(11,"Charmander", 2.50, 15);
        Product eRequest = new Product(12,"Charmeleon", 5.00, 10);

        // Analysis
        assertEquals(eFromUser, trade.getFromUser());
        assertEquals(eToUser, trade.getToUser());
        assertEquals(eOffer, trade.getOffer());
        assertEquals(eRequest, trade.getRequest());
    }

    // Tests getID () method
    @Test
    public void testGetFromUser () {
        //Setup and invoke
        String expected = "fromUser";
        String actual = trade.getFromUser();
        String not = "not this";
        // Analysis
        assertEquals(expected, actual);
        assertNotEquals(not, actual);
    }

    // Tests getName () method
    @Test
    public void testGetToUser () {
        //Setup and invoke
        String expected = "toUser";
        String actual = trade.getToUser();
        String not = "not this";
        // Analysis
        assertEquals(expected, actual);
        assertNotEquals(not, actual);
    }

    // Tests getPrice () method
    @Test
    public void testGetOffer () {
        // Setup and invoke
        Product expected = new Product(11,"Charmander", 2.50, 15);
        Product actual = trade.getOffer();
        Product not = new Product(13,"Charmanders", 2.50, 15);

        // Analysis
        assertEquals(expected, actual);
        assertNotEquals(not, actual);
    }

    // Tests getQuantity () method
    @Test
    public void testGetRequest () {
        // Setup and invoke
        Product expected = new Product(12,"Charmeleon", 5.00, 10);
        Product actual = trade.getRequest();
        Product not = new Product(13,"Charmanders", 2.50, 15);

        // Analysis
        assertEquals(expected, actual);
        assertNotEquals(not, actual);
    }

    // Tests toString () method
    @Test
    public void testToString () {
        // Setup
        String fromUser = "fromUser";
        String toUser = "toUser";

        // Invoke
        String expected_toString = String.format(Trade.STRING_FORMAT, fromUser, toUser);

        // Analysis
        assertEquals(expected_toString, trade.toString());
    }
}
