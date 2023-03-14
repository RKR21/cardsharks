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
 * Handles the REST API requests for the Product resource
 * <p>
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

    public CartController(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @PostMapping("")
    public ResponseEntity<Cart> createCart(@RequestParam String userName) {
        LOG.info("POST /" + userName);
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

    @DeleteMapping("")
    public ResponseEntity<Cart> deleteCart(@RequestParam int token) {
        LOG.info("DELETE /" + token);
        try {
            if(cartDAO.deleteCart(token))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @DeleteMapping("/{token}/{index}")
    public ResponseEntity<HttpStatus> removeFromCart
        (@PathVariable int token, @PathVariable int index)
    {
        LOG.info("DELETE /cart/" + token + "/" + index);
        try {
            if(cartDAO.removeFromCart(token, index))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
