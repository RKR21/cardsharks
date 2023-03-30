package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
 * Handles the REST API requests for the Account resource
 * 
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 *
 * @author Adrian Marcellus
 */
@RestController
@RequestMapping("account")
public class AccountController {
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private AccountDAO accountDAO;

    
    /**
     * Creates a REST API controller to responds to requests
     *
     * @param accountDao The {@link AccountDAO Account Data Access Object} to perform CRUD operations
     * This dependency is injected by the Spring Framework
     */
    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Account account} for the given userName
     *
     * @param userName The user name used to locate the {@link Account account}
     *
     * @return ResponseEntity with {@link Token token} object and HTTP status of OK if found
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Token> logIn(@RequestParam String userName) {
        LOG.info("GET /account?userName=" + userName);
        try {
            Token token = accountDAO.logIn(userName);
            if (token.getToken() != 0)
                return new ResponseEntity<>(token, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Account account} with the provided account object
     *
     * @param userName The user name used create a {@link Account account}
     *
     * @return ResponseEntity with created {@link Account account} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Account account} object already exists
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@RequestParam String userName) {
        LOG.info("POST /account/?userName=" + userName);
        try {
            if(accountDAO.createAccount(userName) != null)
                return new ResponseEntity<>(HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Account account} with the given token
     *
     * @param token token to authenticate
     * @param userName The userName of the {@link Account account} to delete
     *
     * @return ResponseEntity HTTP status of OK if deleted
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{token}")
    public ResponseEntity<Account> deleteAccount
        (@PathVariable int token, @RequestParam String userName) 
    {
        LOG.info("DELETE /account/{" + token + "}?userName=" + userName);
        try {
            if(accountDAO.deleteAccount(token, userName))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Payment payment} with the provided payment object
     * 
     * @param userName - The string userName used to locate the {@linkplain Account account} 
     * @param payment - The {@link Payment payment} to create
     *
     * @return ResponseEntity with created {@link Account account} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of CONFLICT if {@link Account account} object already exists
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("/payment")
    public ResponseEntity<Payment> addPayment
        (@RequestParam String userName, @RequestBody Payment payment) 
    {
        LOG.info("PUT /account/payment/" + userName);
        try {
            if(accountDAO.addToPayments(userName, payment) != null)
                return new ResponseEntity<>(payment,HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Payment payment} with the given userName and payment
     *
     * @param userName The string userName used to locate the {@linkplain Account account} 
     * @param payment - The {@link Payment payment} to create
     *
     * @return ResponseEntity HTTP status of OK if deleted
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/payment")
    public ResponseEntity<Account> removePayment
        (@RequestParam String userName, @RequestBody Payment payment) 
    {
        LOG.info("DELETE /account/payment/?userName=" + userName);
        try {
            if(accountDAO.removeFromPayments(userName, payment))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Payment payment} array for the given user name
     *
     * @param userName The string userName used to locate the {@linkplain Account account} 
     *
     * @return ResponseEntity with {@link Product product} object array and HTTP status of OK if found
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/payment")
    public ResponseEntity<Payment[]> getPayments(@RequestParam String userName) {
        LOG.info("GET /account/payment/?userName=" + userName);
        try {
            Payment[] payments = accountDAO.getPayments(userName);
            if(payments != null)
                return new ResponseEntity<>(payments, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
