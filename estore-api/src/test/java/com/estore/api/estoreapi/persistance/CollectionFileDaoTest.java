package com.estore.api.estoreapi.persistance;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        testCollections[0].addToCollection(new Product(1, "test", 8.4, 3));
        testCollections[0].addToCollection(new Product(2, "test2", 4.8, 4));
        testCollections[1] = new Collection("User2");

        testCollections[1].addToCollection(new Product(2, "test2", 4.8, 4));
        testCollections[2] = new Collection("User3");
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Collection[].class))
                .thenReturn(testCollections);
        collectionFileDAO = new CollectionFileDAO("Doesn't matter", mockObjectMapper);
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
        assertEquals(result, true);
    }

    @Test
    public void testAddToCollection() throws IOException{
        Product toAdd = new Product(4, "New Product", 1000, 1);
        String userName = "user1";
        int token = Account.getToken(userName);
        //Product added = assertDoesNotThrow(()-> collectionFileDAO.addToCollection(token, toAdd));
        Product added = collectionFileDAO.addToCollection(token, toAdd);
        assertNotNull(added);
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
        String userName = "user1";
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
        String user1 = "user1";
        String user2 = "user2";
        int token = Account.getToken(user1);
        Product offer = new Product(1, "Product", 1000, 100);
        Product request = new Product(2, "Proudct2", 100000, 23);

        Trade result = assertDoesNotThrow(()->collectionFileDAO.makeOffer(token, user1, user2, request, offer),
                                     "Unexpected Exception thrown");
        
        assertNotNull(result);


    }

    
}
