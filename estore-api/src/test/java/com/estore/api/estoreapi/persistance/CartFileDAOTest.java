package com.estore.api.estoreapi.persistance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.persistence.CartFileDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Cart File DAO class
 * 
 * @author Adrian Marcellus
 */
@Tag("Persistence-tier")
public class CartFileDAOTest {
    CartFileDAO cartFileDAO;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCarts = new Cart[3];
        testCarts[0] = new Cart("I");
        Product prod =  new Product(1, "Tester", 5.00, 6);
        testCarts[0].addToCart(prod);
        testCarts[1] = new Cart("Dislike");
        testCarts[2] = new Cart("Angular");
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Cart[].class))
                .thenReturn(testCarts);
        cartFileDAO = new CartFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetCart() {
        String user = "I";
        Product[] result = assertDoesNotThrow(() -> cartFileDAO.getCart(user.hashCode()),
                            "Unexpected exception thrown");
        assertNotNull(result);
        assert(result.length == 1);
    }

    @Test
    public void testDeleteCart() throws IOException {
        String user = "Angular";
        boolean result = assertDoesNotThrow(() -> cartFileDAO.deleteCart(user.hashCode()),
                            "Unexpected exception thrown");
        assertEquals(result,true);
    }

    @Test
    public void testCreateCart() {
        String user = "MoreAngular";
        Cart result = assertDoesNotThrow(() -> cartFileDAO.createCart(user),
                            "Unexpected exception thrown");
        assertNotNull(result);
    }

    @Test
    public void testAddToCart() {
        String user = "Angular";
        Product prod =  new Product(3, "Tester", 5.00, 6);
        Product result = assertDoesNotThrow(() -> cartFileDAO.addToCart(user.hashCode(), prod),
                            "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals(prod, result);
    }

    @Test
    public void testRemoveFromCart() {
        String user = "I";
        boolean result = assertDoesNotThrow(() -> cartFileDAO.removeFromCart(user.hashCode(), 0),
                            "Unexpected exception thrown");
        //assert(result);
    }
}


