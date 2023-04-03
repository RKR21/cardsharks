package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Token class
 * 
 * @author Adrian Marcellus
 */
@Tag("Model-tier")
public class TokenTest {
    private Token token;

    // Creates a test product before each test
    @BeforeEach
    public void setupToken () {
        // Constructor Parameters
        int number = 12345;
        this.token = new Token(number);
    }

    // Tests Token constructor to ensure proper object creation
    @Test
    public void testToken() {
        // Analysis
        assertEquals(12345, token.getToken());
    }
}