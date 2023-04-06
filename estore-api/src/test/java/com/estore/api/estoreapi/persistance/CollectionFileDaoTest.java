package com.estore.api.estoreapi.persistance;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.persistence.CollectionFileDAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CollectionFileDaoTest {
    CollectionFileDAO collectionFileDAO;
    Collection[] testCollections;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupCollectionFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        testCollections = new Collection[3];
        testCollections[0] = new Collection("User1");

        testCollections[0].addToCollection(new Product(1, "test", 8.4, 1));
        testCollections[0].addToCollection(new Product(2, "test2", 4.8, 1));
        testCollections[1] = new Collection("User2");

        testCollections[1].addToCollection(new Product(3, "test3", 4.8, 1));
        testCollections[2] = new Collection("User3");
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Collection[].class))
                .thenReturn(testCollections);
        collectionFileDAO = new CollectionFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testCreateCollection() throws IOException{
        String userName = "testUser";
        int token = Account.getToken(userName);
        Collection collection = new Collection(userName);

        Collection result = assertDoesNotThrow(()-> collectionFileDAO.createCollection(userName),
                                "Unexpected exception thrown");
        assertNotNull(result);
        assertNotNull(collectionFileDAO.getCollection(token));
    }

    @Test
    public void testDeleteCollection() throws IOException{
        String userName = "User1";
        int token = Account.getToken(userName);
        boolean result = assertDoesNotThrow(() -> collectionFileDAO.deleteCollection(token),
                                "Unexpected exception thrown");

        boolean deleteNull = collectionFileDAO.deleteCollection(0);
        assertEquals(false, deleteNull);
        assertEquals(result, true);
    }

    @Test
    public void testAddToCollection() throws IOException{
        Product toAdd = new Product(4, "New Product", 1000, 1);
        String userName = "User1";
        int token = Account.getToken(userName);
        //Product added = assertDoesNotThrow(()-> collectionFileDAO.addToCollection(token, toAdd));
        Product added = collectionFileDAO.addToCollection(token, toAdd);
        assertNotNull(added);
        Product addBadToken = collectionFileDAO.addToCollection(0, toAdd);
        assertNull(addBadToken);
        Product[] actual = collectionFileDAO.getCollection(token);

        boolean contains = false;

        for(Product p : actual){
            if(p.equals(toAdd)){
                contains = true;
            }
        }
        assertTrue(contains);
    }

    @Test
    public void testRemoveFromCollection() throws IOException{
        String userName = "User1";
        int token = Account.getToken(userName);
        int id = 1;
        
        Product toRemove = null;
        Product[] initial = collectionFileDAO.getCollection(token);
        for(Product p: initial){
            if(p.getId() == id){
                toRemove = p;
            }
        }

        boolean success = assertDoesNotThrow(()-> collectionFileDAO.removeFromCollection(token, id),
                                "Unexpected exception thrown");
        assertTrue(success);

        boolean removeBadToken = collectionFileDAO.removeFromCollection(0, id);
        assertEquals(false, removeBadToken);

        Product[] actual = collectionFileDAO.getCollection(token);
        
        boolean contains = false;

        for(Product p: actual){
            if(p.equals(toRemove)){
                contains = true;
            }
        }

        assertEquals(contains, false);
    }

    @Test
    public void testMakeOffer() throws IOException{
        String user1 = "User1";
        String user2 = "User2";
        int token = Account.getToken(user1);
        int offerToken = Account.getToken(user2);
        Product offer = new Product(1, "test", 8.4, 1);
        Product request = new Product(3, "test3", 4.8, 1);

        Trade result = assertDoesNotThrow(()->collectionFileDAO.makeOffer(token, user1, user2, offer, request),
                                     "Unexpected Exception thrown");
        Trade actual = collectionFileDAO.getOffer(offerToken);
        assertNotNull(result);
        assertEquals(result, actual);
    }

    @Test
    public void testAcceptOffer() throws IOException{
        //setting up offer
        String user1 = "User1";
        String user2 = "User2";
        int token = Account.getToken(user1);
        int offerToken = Account.getToken(user2);
        Product offer = new Product(1, "test", 8.4, 1);
        Product request = new Product(3, "test3", 4.8, 1);
        collectionFileDAO.makeOffer(token, user1, user2, offer, request);

        boolean result = assertDoesNotThrow(()->collectionFileDAO.acceptOffer(offerToken), "Unexpected exception thrown");
        assertEquals(true, result);
        Product[] user1Col = collectionFileDAO.getCollection(Account.getToken(user1));
        Product[] user2Col = collectionFileDAO.getCollection(Account.getToken(user2));
        boolean user1HasRequest = false;
        boolean user2HasOffer = false;


        //Checking if products changed hands
        for(Product p:user1Col){
            if(p.equals(request)){
                user1HasRequest = true;
            }
        }
        for(Product p:user2Col){
            if(p.equals(offer)){
                user2HasOffer = true;
            }
        }
        
        assert(user1HasRequest);
        assert(user2HasOffer);

    }

    @Test
    public void testRejectOffer() throws IOException{
        //Setting up offer
        String user1 = "User1";
        String user2 = "User2";
        int token = Account.getToken(user1);
        int offerToken = Account.getToken(user2);
        Product offer = new Product(1, "test", 8.4, 1);
        Product request = new Product(3, "test3", 4.8, 1);

        collectionFileDAO.makeOffer(token, user1, user2, offer, request);

        
        boolean success = assertDoesNotThrow(()->collectionFileDAO.rejectOffer(offerToken), "Unexpected exception thrown");
        assert(success);

        Trade actual = collectionFileDAO.getOffer(token);
        assertNull(actual);
    }

    
    
}
