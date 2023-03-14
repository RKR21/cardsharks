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
@RequestMapping("account")
public class AccountController {
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private AccountDAO accountDAO;

    public AccountController(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @GetMapping("")
    public ResponseEntity<Integer> logIn(@RequestParam String userName) {
        LOG.info("GET /account/" + userName);
        try {
            int token = accountDAO.logIn(userName);
            if (token == 0)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        LOG.info("POST /account/" + account.getUserName());
        try {
            if(accountDAO.createAccount(account) != null)
                return new ResponseEntity<>(account,HttpStatus.CREATED);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Account> deleteAccount(@RequestParam String userName) {
        LOG.info("DELETE /account/" + userName);
        try {
            if(accountDAO.deleteAccount(userName))
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
