package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Account class
 * 
 * @author Adrian Marcellus
 */
@Tag("Model-tier")
public class AccountTest {
    private Account account;

    // Creates a test product before each test
    @BeforeEach
    public void setupAccount () {
        // Constructor Parameters
        String userName = "test";
        this.account = new Account(userName);
    }

    // Tests Account constructor to ensure proper object creation
    @Test
    public void testAccount() {
        // Setup
        String expected_name  = "test";
        Payment[] expected_payments = {};
        // Analysis
        assertEquals(expected_name, account.getUserName());
        assertEquals(expected_payments.length, account.getPayments().length);
    }

    // Tests adding payments
    @Test
    public void testAddPayment() {
        // Setup
        String expected_pay  = "testpay";
        Payment testPayment = new Payment(expected_pay);
        //Invoke and Analysis
        assertNotNull(account.addPayment(testPayment));
        assertEquals(testPayment, account.getPayments()[0]);
        assert(account.addPayment(testPayment) == null);
    }

        // Tests removing payments
        @Test
        public void testRemovePayment() {
            // Setup
            String expected_pay  = "testpay";
            Payment testPayment = new Payment(expected_pay);
            //Invoke
            account.addPayment(testPayment);
            //Invoke and analysis
            assert(account.removePayment(testPayment));
            assert(!account.removePayment(testPayment));
        }
}