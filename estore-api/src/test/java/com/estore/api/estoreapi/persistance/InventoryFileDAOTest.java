package com.estore.api.estoreapi.persistance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryFileDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Inventory File DAO class
 * 
 * @author SWEN Faculty
 * @author Adrian Marcellus
 * @author Elijah Lenhard
 */
@Tag("Persistence-tier")
public class InventoryFileDAOTest {
    InventoryFileDAO inventoryFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupInventoryFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(11,"Charmander", 2.50, 15);
        testProducts[1] = new Product(12,"Charmeleon", 5.00, 10);
        testProducts[2] = new Product(13,"Charizard", 7.50, 5);
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Product[].class))
                .thenReturn(testProducts);
        inventoryFileDAO = new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetProducts() throws IOException {
        Product[] products = inventoryFileDAO.getProducts();
        assertEquals(products.length,testProducts.length);
        for (int i = 0; i < testProducts.length;++i)
            assertEquals(products[i],testProducts[i]);
    }

    @Test
    public void testFindProducts() {
        Product[] products = inventoryFileDAO.findProducts("rm");
        assertEquals(products.length,2);
        assertEquals(products[0],testProducts[0]);
        assertEquals(products[1],testProducts[1]);
    }

    @Test
    public void testGetProduct() {
        Product product = inventoryFileDAO.getProduct(11);
        assertEquals(product,testProducts[0]);
    }

    @Test
    public void testDeleteProduct() throws IOException {
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(11),
                            "Unexpected exception thrown");
        assertEquals(result,true);
        assertEquals((inventoryFileDAO.getProducts()).length, testProducts.length-1);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product(14,"Squirtle", 2.50, 15);
        Product result = assertDoesNotThrow(() -> inventoryFileDAO.createProduct(product),
                            "Unexpected exception thrown");
        assertNotNull(result);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual.getId(),product.getId());
        assertEquals(actual.getName(),product.getName());
    }

    @Test
    public void testUpdateProduct(){
        Product product = new Product(11, "Squirtle", 2.50, 15);
        
        Product updated = assertDoesNotThrow(()-> inventoryFileDAO.updateProduct(product),
                "Unexpected exception thrown");
        
        assertNotNull(updated);
        Product actual = inventoryFileDAO.getProduct(product.getId());
        assertEquals(actual, product);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Product[].class));
        Product product = new Product(16, "Pikachu", 8.5, 300);
        assertThrows(IOException.class, () -> inventoryFileDAO.createProduct(product), 
                    "IOException not Thrown");
        
    }

    @Test
    public void testGetProductNotFound(){
        Product product = inventoryFileDAO.getProduct(304);
        assertEquals(product, null);
    }

    @Test
    public void testDeleteProductNotFound(){
        boolean result = assertDoesNotThrow(() -> inventoryFileDAO.deleteProduct(234),
                "Unexpected Exception thrown");

        assertEquals(result, false);
       

    }

    @Test
    public void testUpdateHeroNotFound(){

        Product product = new Product(140, "Pikachu", 30.5, 23);

        Product result = assertDoesNotThrow(() -> inventoryFileDAO.updateProduct(product), "Unexpected exception thrown");

        assertNull(result);

    }

    @Test
    public void testConstructorException() throws IOException{
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Product[].class);
        
        assertThrows(IOException.class, () -> new InventoryFileDAO("doesnt_matter.txt",mockObjectMapper),
                                        "IOException not thrown");
    }

}

