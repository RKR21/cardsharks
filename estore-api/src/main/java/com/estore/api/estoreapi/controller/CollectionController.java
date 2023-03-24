package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.persistence.*;

/**
 * Handles the REST API requests for the Collection resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author Adrian Marcellus
 */

@RestController
@RequestMapping("collection")
public class CollectionController {
    private static final Logger LOG = Logger.getLogger(CollectionController.class.getName());
    private CollectionDAO collectionDAO;

    /**
     * Creates a REST API controller to responds to requests
     *
     * @param collectionDao The {@link CollectionDAO Collection Data Access Object} to perform CRUD operations
     * This dependency is injected by the Spring Framework
     */
    public CollectionController(CollectionDAO collectionDAO) {
        this.collectionDAO = collectionDAO;
    }

    /**
     * Creates a {@linkplain Collection collection} with the provided user name
     *
     * @param userName - The user name of the collection to create
     *
     * @return ResponseEntity with created {@link Collection collection} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Collection collection} object already exists
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Collection> createCollection(@RequestParam String userName) {
        LOG.info("POST /?userName=" + userName);
        try {
            Collection newCollection = collectionDAO.createCollection(userName);
            if(newCollection != null)
                return new ResponseEntity<>(newCollection, HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Collection collection} with the given token
     *
     * @param token The token number of the {@link Collection collection} to deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("")
    public ResponseEntity<Collection> deleteCollection(@RequestParam int token) {
        LOG.info("DELETE /?token=" + token);
        try {
            if(collectionDAO.deleteCollection(token))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Collection collection}  for the given id
     *
     * @param token The id used to locate the {@link Collection collection}
     *
     * @return ResponseEntity with {@link Product product} object array and HTTP status of OK if found
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{token}")
    public ResponseEntity<Product[]> getCollection(@PathVariable int token) {
        LOG.info("GET collection/" + token );
        try {
            Product[] collection = collectionDAO.getCollection(token);
            if(collection == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(collection, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Updates the {@linkplain Collection collection} with the provided {@linkplain Product product} object, if it exists
     *
     * @param token The id used to locate the {@link Collection collection}
     * @param product The {@link Product product} to update
     *
     * @return ResponseEntity with updated {@link Product product} object and HTTP status of OK if updated
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{token}")
    public ResponseEntity<Product> addToCollection
        (@PathVariable int token, @RequestBody Product product) 
    {
        LOG.info("PUT /collection/" + token + "/" + product);
        try {
            Product addedProduct = collectionDAO.addToCollection(token, product);
            if(addedProduct != null)
                return new ResponseEntity<>(addedProduct, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Collection collection} array field with the given 
     *
     * @param token The id used to locate the {@link Collection collection}
     * @param index The index in the {@link Collection collection} array to delete
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{token}/{index}")
    public ResponseEntity<Product> removeFromCollection
        (@PathVariable int token, @PathVariable int index)
    {
        LOG.info("DELETE /collection/" + token + "/" + index);
        try {
            if(collectionDAO.removeFromCollection(token, index))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**<---------TRADE FUNCTIONALITY-------------------------->*/

    /**
     * 
     * @param token
     * @param userName
     * @param otherName
     * @param request
     * @param offer
     * 
     * @return
     */
    @PostMapping("/offer/{token}")
    public ResponseEntity<Trade> makeOffer(@PathVariable int token, 
    @RequestParam String userName, @RequestParam String otherName, 
    @RequestBody Product request, @RequestBody Product offer)
    {
        LOG.info("PUT /collection/offer/{" + token
        + "}?userName=" + userName + "?otherName=" + otherName
        + " RO:" +  request + offer);
        try {
            Trade tradeOffer = 
            collectionDAO.makeOffer(token, userName, otherName, request, offer);
            if(tradeOffer != null)
                return new ResponseEntity<Trade>(tradeOffer, HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param token
     * 
     * @return
     */
    @PutMapping("/offer-accept/{token}")
    public ResponseEntity<Product> acceptOffer(@PathVariable int token){
        LOG.info("PUT /collection/offer-accept/{" + token + "}");
        try {
            if(collectionDAO.acceptOffer(token))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 
     * @param token
     * 
     * @return
     */
    @PutMapping("/offer-reject/{token}")
    public ResponseEntity<Product> rejectOffer(@PathVariable int token){
        LOG.info("PUT /collection/offer-reject/{" + token + "}");
        try {
            if(collectionDAO.rejectOffer(token))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 
     * @param token
     * 
     * @return
     */
    @GetMapping("/offer/{token}")
    public ResponseEntity<Trade> getOffer(@PathVariable int token)
    {
        LOG.info("GET /collection/offer/{" + token + "}");
        try {
            Trade tradeOffer = collectionDAO.getOffer(token);
            if(tradeOffer != null)
                return new ResponseEntity<>(tradeOffer, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
