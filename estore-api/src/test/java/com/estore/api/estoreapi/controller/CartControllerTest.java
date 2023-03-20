package com.estore.api.estoreapi.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.*;
import com.estore.api.estoreapi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Cart Controller class
 * 
 * @author Adrian Marcellus
 */
@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO cartDAO;
    private final int cartToken = 12345;
    private final String userName = "user";
    private Product testProduct = new Product(1, "tester", 5.00, 3); 

    /**
     * Before each test, create a new CartController object and inject
     * a mock Cart DAO
     */
    @BeforeEach
    public void setupCartController() {
        cartDAO = mock(CartDAO.class);
        cartController = new CartController(cartDAO);
        new Product(1, "tester", 5.00, 3);
    }

    // Tests getCart () method to ensure correct status codes are produced when
    // a cart with the searched id exists
    @Test
    public void testGetCart() throws IOException {
        // Setup
        Cart cart = new Cart(userName);
        cart.addToCart(testProduct);
        when(cartDAO.getCart(cartToken)).thenReturn(cart.getCart());

        // Invoke
        ResponseEntity<Product[]> response = cartController.getCart(cartToken);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    // Tests getCart () method to ensure correct status codes are produced when
    // a cart does not exist
    @Test
    public void testGetCartNotFound() throws Exception {
        // Setup
        when(cartDAO.getCart(cartToken)).thenReturn(null);

        // Invoke
        ResponseEntity<Product[]> response = cartController.getCart(cartToken);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests getCart () method to ensure correct status codes are produced when
    // a server error occurs while getting the cart
    @Test
    public void testGetCartHandleException() throws Exception {
        // Setup
        doThrow(new IOException()).when(cartDAO).getCart(cartToken);

        // Invoke
        ResponseEntity<Product[]> response = cartController.getCart(cartToken);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests createCart () method to ensure correct status codes are produced
    // when a cart is properly created.
    @Test
    public void testCreateCart() throws IOException {
        // Invoke
        Cart cart = new Cart(userName);
        when(cartDAO.createCart(userName)).thenReturn(cart);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(userName);

        // Analysis
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(cart,response.getBody());
    }

    // Tests createCart () method to ensure correct status codes are produced
    // when an attempted cart creation uses an existing cart id
    @Test
    public void testCreateCartFailed() throws IOException {
        // Setup
        when(cartDAO.createCart(userName)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(userName);

        // Analysis
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    // Tests createCart () method to ensure correct status codes are produced
    // when a server error occurs during creation
    @Test
    public void testCreateCartHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(cartDAO).createCart(userName);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(userName);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    // Tests deleteCart () method to ensure correct status codes are
    // produced when a cart is successfully deleted
    @Test
    public void testDeleteCart() throws IOException {
        // Setup
        when(cartDAO.deleteCart(cartToken)).thenReturn(true);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(cartToken);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    // Tests deleteCart () method to ensure correct status codes are
    // produced when the requested cart to be deleted does not exist
    @Test
    public void testDeleteCartNotFound() throws IOException {
        // Setup
        when(cartDAO.deleteCart(cartToken)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(cartToken);

        //Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests deleteCart () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testDeleteCartHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(cartDAO).deleteCart(cartToken);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(cartToken);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests addToCart () method to ensure correct status codes are
    // produced when a cart is successfully deleted
    @Test
    public void testAddToCart() throws IOException {
        // Setup
        when(cartDAO.addToCart(cartToken, testProduct)).thenReturn(testProduct);

        // Invoke
        ResponseEntity<Product> response = cartController.addToCart(cartToken, testProduct);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testProduct,response.getBody());
    }

    // Tests addToCart () method to ensure correct status codes are
    // produced when the requested cart to be deleted does not exist
    @Test
    public void testAddToCartNotFound() throws IOException {
        // Setup
        when(cartDAO.addToCart(cartToken, testProduct)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = cartController.addToCart(cartToken, testProduct);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests addToCart () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testAddToCartHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(cartDAO).addToCart(cartToken, testProduct);

        // Invoke
        ResponseEntity<Product> response = cartController.addToCart(cartToken, testProduct);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
