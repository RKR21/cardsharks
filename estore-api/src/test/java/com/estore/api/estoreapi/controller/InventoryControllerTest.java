package com.estore.api.estoreapi.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.controller.InventoryController;
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

    @Test
    public void testGetProduct() throws IOException {
        Product product = new Product(5,"Charmander", 5.99, 5);
        when(inventoryDAO.getProduct(product.getId())).thenReturn(product);
        ResponseEntity<Product> response = inventoryController.getProduct(product.getId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception {
        int id = 9001;
        when(inventoryDAO.getProduct(id)).thenReturn(null);
        ResponseEntity<Product> response = inventoryController.getProduct(id);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws Exception {
        int id = 9001;
        doThrow(new IOException()).when(inventoryDAO).getProduct(id);
        ResponseEntity<Product> response = inventoryController.getProduct(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateProduct() throws IOException {
        Product product = new Product(5,"Bulbasaur", 5.99, 5);
        when(inventoryDAO.createProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testCreateProductFailed() throws IOException {
        Product product = new Product(5,"Squirtle", 5.99, 5);
        when(inventoryDAO.createProduct(product)).thenReturn(null);
        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateProductHandleException() throws IOException {
        Product product = new Product(5,"Squirtle", 5.99, 5);
        doThrow(new IOException()).when(inventoryDAO).createProduct(product);
        ResponseEntity<Product> response = inventoryController.createProduct(product);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws IOException {
        Product product = new Product(5,"Squirtle", 5.99, 5);
        when(inventoryDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = inventoryController.updateProduct(product);
        product.setName("GeoDude");
        response = inventoryController.updateProduct(product);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException {
        Product product = new Product(5,"Squirtle", 5.99, 5);
        when(inventoryDAO.updateProduct(product)).thenReturn(null);
        ResponseEntity<Product> response = inventoryController.updateProduct(product);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException {
        Product product = new Product(5,"Squirtle", 5.99, 5);
        doThrow(new IOException()).when(inventoryDAO).updateProduct(product);
        ResponseEntity<Product> response = inventoryController.updateProduct(product);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws IOException {
        Product[] products = new Product[2];
        products[0] = new Product(5,"Squirtle", 5.99, 5);
        products[1] = new Product(23,"Garchomp", 10.99, 2);
        when(inventoryDAO.getProducts()).thenReturn(products);
        ResponseEntity<Product[]> response = inventoryController.getProducts();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testGetProductsHandleException() throws IOException {
        doThrow(new IOException()).when(inventoryDAO).getProducts();
        ResponseEntity<Product[]> response = inventoryController.getProducts();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchProducts() throws IOException {
        String searchString = "har";
        Product[] products = new Product[2];
        products[0] = new Product(5,"Charmander", 5.99, 5);
        products[1] = new Product(23,"Charmeleon", 10.99, 2);
        when(inventoryDAO.findProducts(searchString)).thenReturn(products);
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testSearchProductsHandleException() throws IOException {
        String searchString = "tle";
        doThrow(new IOException()).when(inventoryDAO).findProducts(searchString);
        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException {
        int id = 9001;
        when(inventoryDAO.deleteProduct(id)).thenReturn(true);
        ResponseEntity<Product> response = inventoryController.deleteProduct(id);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException {
        int id = 9001;
        when(inventoryDAO.deleteProduct(id)).thenReturn(false);
        ResponseEntity<Product> response = inventoryController.deleteProduct(id);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException {
        int id = 9001;
        doThrow(new IOException()).when(inventoryDAO).deleteProduct(id);
        ResponseEntity<Product> response = inventoryController.deleteProduct(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
