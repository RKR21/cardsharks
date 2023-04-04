package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Collection class
 * 
 * @author Adrian Marcellus
 */
@Tag("Model-tier")
public class CollectionTest {
    private Collection collection;
    private Product testProduct = new Product
        (11,"Charmander", 2.50, 15);

    // Creates a test product before each test
    @BeforeEach
    public void setupCollection () {
        // Constructor Parameters
        String userName = "test";
        this.collection = new Collection(userName);
    }

    // Tests Collection constructor to ensure proper object creation
    @Test
    public void testCollection() {
        // Setup
        String expected_name  = "test";
        Product[] expected_collection = {};
        // Analysis
        assertEquals(Account.getToken(expected_name), collection.getToken());
        assertEquals(expected_collection.length, collection.getCollection().length);
    }

    // Tests adding products
    @Test
    public void testAddToCollection() {
        //Invoke and Analysis
        assertNotNull(collection.addToCollection(testProduct));
        assertEquals(testProduct, collection.getCollection()[0]);
        assert(collection.addToCollection(testProduct).getQuantity() == 2);
    }

    // Tests removing products
    @Test
    public void testRemoveFromCollection() {
        //Setup
        collection.addToCollection(testProduct);
        //Invoke and analysis
        assert(collection.removeFromCollection(testProduct.getId()));
        assert(!collection.removeFromCollection(testProduct.getId()));
        collection.addToCollection(testProduct);
        collection.addToCollection(testProduct);
        collection.removeFromCollection(testProduct.getId());
        assert(collection.getCollection()[0].getQuantity() == 1);
    }

    // Tests toString () method
    @Test
    public void testToString () {
        // Setup
        String userName = "test";

        // Invoke
        String expected_toString = String.format(Collection.STRING_FORMAT, userName);

        // Analysis
        assertEquals(expected_toString, collection.toString());
    }
}