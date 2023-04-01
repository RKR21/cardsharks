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
 * Test the Collection Controller class
 * 
 * @author Adrian Marcellus
 */
@Tag("Controller-tier")
public class CollectionControllerTest {
    private CollectionController collectionController;
    private CollectionDAO collectionDAO;
    private final int collectionToken = 12345;
    private final String userName = "user";
    private Product testProduct = new Product(1, "tester", 5.00, 3); 

    /**
     * Before each test, create a new CollectionController object and inject
     * a mock Collection DAO
     */
    @BeforeEach
    public void setupCollectionController() {
        collectionDAO = mock(CollectionDAO.class);
        collectionController = new CollectionController(collectionDAO);
        new Product(1, "tester", 5.00, 3);
    }

    // Tests getCollection () method to ensure correct status codes are produced when
    // a collection with the searched id exists
    @Test
    public void testGetCollection() throws IOException {
        // Setup
        Collection collection = new Collection(userName);
        collection.addToCollection(testProduct);
        when(collectionDAO.getCollection(collectionToken)).thenReturn(collection.getCollection());

        // Invoke
        ResponseEntity<Product[]> response = collectionController.getCollection(collectionToken);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    // Tests getCollection () method to ensure correct status codes are produced when
    // a collection does not exist
    @Test
    public void testGetCollectionNotFound() throws Exception {
        // Setup
        when(collectionDAO.getCollection(collectionToken)).thenReturn(null);

        // Invoke
        ResponseEntity<Product[]> response = collectionController.getCollection(collectionToken);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests getCollection () method to ensure correct status codes are produced when
    // a server error occurs while getting the collection
    @Test
    public void testGetCollectionHandleException() throws Exception {
        // Setup
        doThrow(new IOException()).when(collectionDAO).getCollection(collectionToken);

        // Invoke
        ResponseEntity<Product[]> response = collectionController.getCollection(collectionToken);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests createCollection () method to ensure correct status codes are produced
    // when a collection is properly created.
    @Test
    public void testCreateCollection() throws IOException {
        // Invoke
        Collection collection = new Collection(userName);
        when(collectionDAO.createCollection(userName)).thenReturn(collection);

        // Invoke
        ResponseEntity<Collection> response = collectionController.createCollection(userName);

        // Analysis
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(collection,response.getBody());
    }

    // Tests createCollection () method to ensure correct status codes are produced
    // when an attempted collection creation uses an existing collection id
    @Test
    public void testCreateCollectionFailed() throws IOException {
        // Setup
        when(collectionDAO.createCollection(userName)).thenReturn(null);

        // Invoke
        ResponseEntity<Collection> response = collectionController.createCollection(userName);

        // Analysis
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    // Tests createCollection () method to ensure correct status codes are produced
    // when a server error occurs during creation
    @Test
    public void testCreateCollectionHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(collectionDAO).createCollection(userName);

        // Invoke
        ResponseEntity<Collection> response = collectionController.createCollection(userName);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    // Tests deleteCollection () method to ensure correct status codes are
    // produced when a collection is successfully deleted
    @Test
    public void testDeleteCollection() throws IOException {
        // Setup
        when(collectionDAO.deleteCollection(collectionToken)).thenReturn(true);

        // Invoke
        ResponseEntity<Collection> response = collectionController.deleteCollection(collectionToken);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    // Tests deleteCollection () method to ensure correct status codes are
    // produced when the requested collection to be deleted does not exist
    @Test
    public void testDeleteCollectionNotFound() throws IOException {
        // Setup
        when(collectionDAO.deleteCollection(collectionToken)).thenReturn(false);

        // Invoke
        ResponseEntity<Collection> response = collectionController.deleteCollection(collectionToken);

        //Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests deleteCollection () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testDeleteCollectionHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(collectionDAO).deleteCollection(collectionToken);

        // Invoke
        ResponseEntity<Collection> response = collectionController.deleteCollection(collectionToken);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests addToCollection () method to ensure correct status codes are
    // produced when a collection is successfully deleted
    @Test
    public void testAddToCollection() throws IOException {
        // Setup
        when(collectionDAO.addToCollection(collectionToken, testProduct)).thenReturn(testProduct);

        // Invoke
        ResponseEntity<Product> response = collectionController.addToCollection(collectionToken, testProduct);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testProduct,response.getBody());
    }

    // Tests addToCollection () method to ensure correct status codes are
    // produced when the requested collection to be deleted does not exist
    @Test
    public void testAddToCollectionNotFound() throws IOException {
        // Setup
        when(collectionDAO.addToCollection(collectionToken, testProduct)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = collectionController.addToCollection(collectionToken, testProduct);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests addToCollection () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testAddToCollectionHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(collectionDAO).addToCollection(collectionToken, testProduct);

        // Invoke
        ResponseEntity<Product> response = collectionController.addToCollection(collectionToken, testProduct);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests removeFromCollection() method to ensure correct status codes are
    // produced when removing from a collection
    @Test
    public void testRemoveFromCollection() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        int id = 11;

        when(collectionDAO.removeFromCollection(token, 11)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = collectionController.removeFromCollection(token, id);

        // Analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Tests removeFromCollection() method to ensure correct status codes are
    // produced when removing from a collection
    @Test
    public void testRemoveFromCollectionNotFound() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        int id = 11;

        when(collectionDAO.removeFromCollection(token, 11)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = collectionController.removeFromCollection(token, id);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Tests removeFromCollection() method to ensure correct status codes are
    // produced when removing from a collection
    @Test
    public void testRemoveFromCollectionHandleException() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        int id = 11;

        doThrow(new IOException()).when(collectionDAO).removeFromCollection(token, id);

        // Invoke
        ResponseEntity<Product> response = collectionController.removeFromCollection(token, id);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests acceptOffer() method to ensure correct status codes are
    // produced when accepting a pending offer
    @Test
    public void testAcceptOffer() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);

        when(collectionDAO.acceptOffer(token)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = collectionController.acceptOffer(token);

        // Analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Tests acceptOffer() method to ensure correct status codes are
    // produced when accepting a pending offer
    @Test
    public void testAcceptOfferNotFound() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);

        when(collectionDAO.acceptOffer(token)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = collectionController.acceptOffer(token);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Tests acceptOffer() method to ensure correct status codes are
    // produced when accepting a pending offer
    @Test
    public void testAcceptOfferHandleException() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);

        doThrow(new IOException()).when(collectionDAO).acceptOffer(token);

        // Invoke
        ResponseEntity<Product> response = collectionController.acceptOffer(token);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests rejectOffer() method to ensure correct status codes are
    // produced when rejecting a pending offer
    @Test
    public void testRejectOffer() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);

        when(collectionDAO.rejectOffer(token)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = collectionController.rejectOffer(token);

        // Analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Tests rejectOffer() method to ensure correct status codes are
    // produced when rejecting a pending offer
    @Test
    public void testRejectOfferNotFound() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);

        when(collectionDAO.rejectOffer(token)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = collectionController.rejectOffer(token);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Tests rejectOffer() method to ensure correct status codes are
    // produced when rejecting a pending offer
    @Test
    public void testRejectOfferHandleException() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);

        doThrow(new IOException()).when(collectionDAO).rejectOffer(token);

        // Invoke
        ResponseEntity<Product> response = collectionController.rejectOffer(token);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests getOffer() method to ensure correct status codes are produced when
    // getting a pending offer if it exists
    @Test
    public void testGetOffer() throws IOException {
        // Setup
        String fromUser = "fromUser";
        String toUser = "toUser";
        Product offer = new Product(11,"Charmander", 2.50, 15);
        Product request = new Product(12,"Charmeleon", 5.00, 10);

        int token = Account.getToken(toUser);
        Trade trade = new Trade(fromUser, toUser, offer, request);

        when(collectionDAO.getOffer(token)).thenReturn(trade);

        // Invoke
        ResponseEntity<Trade> response = collectionController.getOffer(token);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(trade.getOffer(), response.getBody().getOffer());
        assertEquals(trade.getRequest(), response.getBody().getRequest());
    }

    // Tests getOffer() method to ensure correct status codes are produced when
    // getting a pending offer if it exists
    @Test
    public void testGetOfferNotFound() throws Exception {
        // Setup
        when(collectionDAO.getOffer(123)).thenReturn(null);

        // Invoke
        ResponseEntity<Trade> response = collectionController.getOffer(123);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests getOffer() method to ensure correct status codes are produced when
    // getting a pending offer if it exists
    @Test
    public void testGetOfferHandleException() throws Exception {
        // Setup
        doThrow(new IOException()).when(collectionDAO).getOffer(123);

        // Invoke
        ResponseEntity<Trade> response = collectionController.getOffer(123);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests makeOffer() method to ensure correct status codes are produced when
    // making a offer and its validity
    @Test
    public void testMakeOffer() throws IOException {
        // Setup
        String fromUser = "fromUser";
        String toUser = "toUser";
        Product offer = new Product(11,"Charmander", 2.50, 15);
        Product request = new Product(12,"Charmeleon", 5.00, 10);

        int token = Account.getToken(toUser);
        Trade trade = new Trade(fromUser, toUser, offer, request);

        when(collectionDAO.makeOffer(token, fromUser, toUser, offer, request)).thenReturn(trade);

        // Invoke
        ResponseEntity<Trade> response = collectionController.makeOffer(token, fromUser, toUser, offer, request);

        // Analysis
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(trade.getOffer(), response.getBody().getOffer());
        assertEquals(trade.getRequest(), response.getBody().getRequest());
    }

    // Tests makeOffer() method to ensure correct status codes are produced when
    // making a offer and its validity
    @Test
    public void testMakeOfferConflict() throws Exception {
        // Setup
        String fromUser = "fromUser";
        String toUser = "toUser";
        Product offer = new Product(11,"Charmander", 2.50, 15);
        Product request = new Product(12,"Charmeleon", 5.00, 10);

        int token = Account.getToken(toUser);

        when(collectionDAO.makeOffer(token, fromUser, toUser, offer, request)).thenReturn(null);

        // Invoke
        ResponseEntity<Trade> response = collectionController.makeOffer(token, fromUser, toUser, offer, request);

        // Analysis
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    // Tests makeOffer() method to ensure correct status codes are produced when
    // making a offer and its validity
    @Test
    public void testMakeOfferHandleException() throws Exception {
        // Setup
        String fromUser = "fromUser";
        String toUser = "toUser";
        Product offer = new Product(11,"Charmander", 2.50, 15);
        Product request = new Product(12,"Charmeleon", 5.00, 10);

        int token = Account.getToken(toUser);

        doThrow(new IOException()).when(collectionDAO).makeOffer(token, fromUser, toUser, offer, request);

        // Invoke
        ResponseEntity<Trade> response = collectionController.makeOffer(token, fromUser, toUser, offer, request);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
