package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Payment class
 * 
 * @author Adrian Marcellus
 */
@Tag("Model-tier")
public class PaymentTest {
    private Payment payment;

    // Creates a test product before each test
    @BeforeEach
    public void setupPayment () {
        // Constructor Parameters
        String userName = "test";
        this.payment = new Payment(userName);
    }

    // Tests Payment constructor to ensure proper object creation
    @Test
    public void testPayment() {
        // Setup
        String expected_type  = "test";
        // Analysis
        assertEquals(expected_type, payment.getType());
    }
}