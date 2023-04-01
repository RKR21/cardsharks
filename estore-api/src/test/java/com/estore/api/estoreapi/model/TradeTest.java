package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
