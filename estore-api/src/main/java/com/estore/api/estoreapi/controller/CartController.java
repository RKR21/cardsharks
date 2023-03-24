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
 * Handles the REST API requests for the Cart resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author Adrian Marcellus
 */

@RestController
@RequestMapping("cart")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDAO;

    /**
     * Creates a REST API controller to responds to requests
     *
     * @param cartDao The {@link CartDAO Cart Data Access Object} to perform CRUD operations
     * This dependency is injected by the Spring Framework
     */
    public CartController(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    /**
     * Creates a {@linkplain Cart cart} with the provided user name
     *
     * @param userName - The user name of the cart to create
     *
     * @return ResponseEntity with created {@link Cart cart} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Cart cart} object already exists
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Cart> createCart(@RequestParam String userName) {
        LOG.info("POST /?userName=" + userName);
        try {
            Cart newCart = cartDAO.createCart(userName);
            if(newCart != null)
                return new ResponseEntity<>(newCart, HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Cart cart} with the given token
     *
     * @param token The token number of the {@link Cart cart} to deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("")
    public ResponseEntity<Cart> deleteCart(@RequestParam int token) {
        LOG.info("DELETE /?token=" + token);
        try {
            if(cartDAO.deleteCart(token))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Cart cart}  for the given id
     *
     * @param token The id used to locate the {@link Cart cart}
     *
     * @return ResponseEntity with {@link Product product} object array and HTTP status of OK if found
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{token}")
    public ResponseEntity<Product[]> getCart(@PathVariable int token) {
        LOG.info("GET cart/" + token );
        try {
            Product[] cart = cartDAO.getCart(token);
            if(cart == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Updates the {@linkplain Cart cart} with the provided {@linkplain Product product} object, if it exists
     *
     * @param token The id used to locate the {@link Cart cart}
     * @param product The {@link Product product} to update
     *
     * @return ResponseEntity with updated {@link Product product} object and HTTP status of OK if updated
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{token}")
    public ResponseEntity<Product> addToCart
        (@PathVariable int token, @RequestBody Product product) 
    {
        LOG.info("PUT /cart/" + token + "/" + product);
        try {
            Product addedProduct = cartDAO.addToCart(token, product);
            if(addedProduct != null)
                return new ResponseEntity<>(addedProduct, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Cart cart} array field with the given 
     *
     * @param token The id used to locate the {@link Cart cart}
     * @param id product id to remove
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{token}/{index}")
    public ResponseEntity<Product> removeFromCart
        (@PathVariable int token, @PathVariable int id)
    {
        LOG.info("DELETE /cart/" + token + "/" + id);
        try {
            if(cartDAO.removeFromCart(token, id))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
