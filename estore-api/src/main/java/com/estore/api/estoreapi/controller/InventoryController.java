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
import com.persistence.InventoryDAO;
import com.estore.api.estoreapi.model.Product;

/**
 * Handles the REST API requests for the Hero resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author SWEN Faculty
 * @author Adrian Marcellus
 */

@RestController
@RequestMapping("products")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDAO inventoryDao;

    /**
     * Creates a REST API controller to responds to requests
     *
     * @param heroDao The {@link HeroDAO Hero Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public InventoryController(InventoryDAO inventoryDao) {
        this.inventoryDao = inventoryDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Hero hero} for the given id
     *
     * @param id The id used to locate the {@link Hero hero}
     *
     * @return ResponseEntity with {@link Hero hero} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = inventoryDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<>(product,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Hero heroes}
     *
     * @return ResponseEntity with array of {@link Hero hero} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {
        LOG.info("GET /products");

        try {
            Product[] products = inventoryDao.getProducts();
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Hero heroes} whose name contains
     * the text in name
     *
     * @param name The name parameter which contains the text used to find the {@link Hero heroes}
     *
     * @return ResponseEntity with array of {@link Hero hero} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all heroes that contain the text "ma"
     * GET http://localhost:8080/heroes/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String name) {
        LOG.info("GET /products/?name="+name);
        try {
            return new ResponseEntity<>(inventoryDao.findProducts(name),HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Hero hero} with the provided hero object
     *
     * @param hero - The {@link Hero hero} to create
     *
     * @return ResponseEntity with created {@link Hero hero} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Hero hero} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> createHero(@RequestBody Product product) {
        LOG.info("POST /products " + product);
        try {
            if(inventoryDao.createProduct(product) != null)
                return new ResponseEntity<>(product,HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Hero hero} with the provided {@linkplain Hero hero} object, if it exists
     *
     * @param hero The {@link Hero hero} to update
     *
     * @return ResponseEntity with updated {@link Hero hero} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> updateHero(@RequestBody Product product) {
        LOG.info("PUT /products " + product);
        try {
            if(inventoryDao.updateProduct(product) != null)
                return new ResponseEntity<>(product,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Hero hero} with the given id
     *
     * @param id The id of the {@link Hero hero} to deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteHero(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);
        try {
            if(inventoryDao.deleteProduct(id))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
