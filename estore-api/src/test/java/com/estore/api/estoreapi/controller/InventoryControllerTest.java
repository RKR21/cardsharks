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
 * Test the Inventory Controller class
 * 
 * @author Adrian Marcellus
 * @author Joshua Matthews (added comments)
 */
@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDAO inventoryDAO;

    /**
     * Before each test, create a new InventoryController object and inject
     * a mock Inventory DAO
     */
    @BeforeEach
    public void setupInventoryController() {
        inventoryDAO = mock(InventoryDAO.class);
        inventoryController = new InventoryController(inventoryDAO);
    }

    // Tests getProduct () method to ensure correct status codes are produced when
    // a product with the searched id exists
    @Test
    public void testGetProduct() throws IOException {
        // Setup
        Product product = new Product(5,"Charmander", 5.99, 5);
        when(inventoryDAO.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(product.getId());

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    // Tests getProduct () method to ensure correct status codes are produced when
    // a product does not exist
    @Test
    public void testGetProductNotFound() throws Exception {
        // Setup
        int id = 9001;
        when(inventoryDAO.getProduct(id)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(id);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests getProduct () method to ensure correct status codes are produced when
    // a server error occurs while getting the product
    @Test
    public void testGetProductHandleException() throws Exception {
        // Setup
        int id = 9001;
        doThrow(new IOException()).when(inventoryDAO).getProduct(id);

        // Invoke
        ResponseEntity<Product> response = inventoryController.getProduct(id);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests createProduct () method to ensure correct status codes are produced
    // when a product is properly created.
    @Test
    public void testCreateProduct() throws IOException {
        // Invoke
        Product product = new Product(5,"Bulbasaur", 5.99, 5);
        when(inventoryDAO.createProduct(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        // Analysis
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    // Tests createProduct () method to ensure correct status codes are produced
    // when an attempted product creation uses an existing product id
    @Test
    public void testCreateProductFailed() throws IOException {
        // Setup
        Product product = new Product(5,"Squirtle", 5.99, 5);
        when(inventoryDAO.createProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        // Analysis
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    // Tests createProduct () method to ensure correct status codes are produced
    // when a server error occurs during creation
    @Test
    public void testCreateProductHandleException() throws IOException {
        // Setup
        Product product = new Product(5,"Squirtle", 5.99, 5);
        doThrow(new IOException()).when(inventoryDAO).createProduct(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.createProduct(product);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests updateProduct () method to ensure correct status codes are produced
    // when a product is properly updated
    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product = new Product(5,"Squirtle", 5.99, 5);
        when(inventoryDAO.updateProduct(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.updateProduct(product);
        product.setName("GeoDude");
        response = inventoryController.updateProduct(product);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    // Tests updateProduct () method to ensure correct status codes are produced
    // when attempting to update a product that does not exist
    @Test
    public void testUpdateProductFailed() throws IOException {
        // Setup
        Product product = new Product(5,"Squirtle", 5.99, 5);
        when(inventoryDAO.updateProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = inventoryController.updateProduct(product);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests updateProduct () method to ensure correct status codes are produced
    // when a server error occurs during update execution
    @Test
    public void testUpdateProductHandleException() throws IOException {
        // Setup
        Product product = new Product(5,"Squirtle", 5.99, 5);
        doThrow(new IOException()).when(inventoryDAO).updateProduct(product);

        // Invoke
        ResponseEntity<Product> response = inventoryController.updateProduct(product);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests getProducts () method to ensure correct status codes are produced when
    // products exist and are successfully retrieved
    @Test
    public void testGetProducts() throws IOException {
        // Setup
        Product[] products = new Product[2];
        products[0] = new Product(5,"Squirtle", 5.99, 5);
        products[1] = new Product(23,"Garchomp", 10.99, 2);
        when(inventoryDAO.getProducts()).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.getProducts();

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    // Tests getProducts () method to ensure correct status codes are produced when
    // a server error occurs during method execution
    @Test
    public void testGetProductsHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(inventoryDAO).getProducts();

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.getProducts();

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests searchProducts () method to ensure correct status codes are produced
    // when the product being searched for exists
    @Test
    public void testSearchProducts() throws IOException {
        // Setup
        String searchString = "har";
        Product[] products = new Product[2];
        products[0] = new Product(5,"Charmander", 5.99, 5);
        products[1] = new Product(23,"Charmeleon", 10.99, 2);
        when(inventoryDAO.findProducts(searchString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    // Tests searchProducts method to ensure correct status codes are produced
    // when a server error occurs during method execution
    @Test
    public void testSearchProductsHandleException() throws IOException {
        // Setup
        String searchString = "tle";
        doThrow(new IOException()).when(inventoryDAO).findProducts(searchString);

        // Invoke
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests deleteProduct () method to ensure correct status codes are
    // produced when a product is successfully deleted
    @Test
    public void testDeleteProduct() throws IOException {
        // Setup
        int id = 9001;
        when(inventoryDAO.deleteProduct(id)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(id);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    // Tests deleteProduct () method to ensure correct status codes are
    // produced when the requested product to be deleted does not exist
    @Test
    public void testDeleteProductNotFound() throws IOException {
        // Setup
        int id = 9001;
        when(inventoryDAO.deleteProduct(id)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(id);

        //Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests deleteProduct () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testDeleteProductHandleException() throws IOException {
        // Setup
        int id = 9001;
        doThrow(new IOException()).when(inventoryDAO).deleteProduct(id);

        // Invoke
        ResponseEntity<Product> response = inventoryController.deleteProduct(id);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
