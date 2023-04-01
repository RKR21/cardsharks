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
 * Test the Account Controller class
 * 
 * @author Adrian Marcellus
 */
@Tag("Controller-tier")
public class AccountControllerTest {
    private AccountController accountController;
    private AccountDAO accountDAO;

    /**
     * Before each test, create a new AccountController object and inject
     * a mock Account DAO
     */
    @BeforeEach
    public void setupAccountController() { 
        accountDAO = mock(AccountDAO.class);
        accountController = new AccountController(accountDAO);
    }

    // Tests getaccount () method to ensure correct status codes are produced when
    // a account with the searched id exists
    @Test
    public void testLogIn() throws IOException {
        // Setup
        String userName = "user";
        Token token = new Token(12345);
        when(accountDAO.logIn(userName)).thenReturn(token);

        // Invoke
        ResponseEntity<Token> response = accountController.logIn(userName);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(token,response.getBody());
    }

    // Tests getAccount () method to ensure correct status codes are produced when
    // a Account does not exist
    @Test
    public void testLogInNotFound() throws Exception {
        // Setup
        String userName = "user";
        Token token = new Token(0);
        when(accountDAO.logIn(userName)).thenReturn(token);

        // Invoke
        ResponseEntity<Token> response = accountController.logIn(userName);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests getAccount () method to ensure correct status codes are produced when
    // a server error occurs while getting the Account
    @Test
    public void testLogInHandleException() throws Exception {
        // Setup
        String user = "user";
        doThrow(new IOException()).when(accountDAO).logIn(user);

        // Invoke
        ResponseEntity<Token> response = accountController.logIn(user);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests createAccount () method to ensure correct status codes are produced
    // when a Account is properly created.
    @Test
    public void testCreateAccount() throws IOException {
        // Invoke
        Account account = new Account("user");
        when(accountDAO.createAccount(account.getUserName())).thenReturn(account);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account.getUserName());

        // Analysis
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        //assertEquals(account,response.getBody());
    }

    // Tests createAccount () method to ensure correct status codes are produced
    // when an attempted Account creation uses an existing Account id
    @Test
    public void testCreateAccountFailed() throws IOException {
        // Setup
        Account account = new Account("user");
        when(accountDAO.createAccount(account.getUserName())).thenReturn(null);

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account.getUserName());

        // Analysis
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    // Tests createAccount () method to ensure correct status codes are produced
    // when a server error occurs during creation
    @Test
    public void testCreateAccountHandleException() throws IOException {
        // Setup
        Account account = new Account("user");
        doThrow(new IOException()).when(accountDAO).createAccount(account.getUserName());

        // Invoke
        ResponseEntity<Account> response = accountController.createAccount(account.getUserName());

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests deleteAccount () method to ensure correct status codes are
    // produced when a Account is successfully deleted
    @Test
    public void testDeleteAccount() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        when(accountDAO.deleteAccount(token, user)).thenReturn(true);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(token, user);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    // Tests deleteAccount () method to ensure correct status codes are
    // produced when the requested Account to be deleted does not exist
    @Test
    public void testDeleteAccountNotFound() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        when(accountDAO.deleteAccount(token, user)).thenReturn(false);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(token, user);

        //Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests deleteAccount () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testDeleteAccountHandleException() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        doThrow(new IOException()).when(accountDAO).deleteAccount(token, user);

        // Invoke
        ResponseEntity<Account> response = accountController.deleteAccount(token, user);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests getPayments() method to ensure correct status codes are
    // produced when getting a payments
    @Test
    public void testGetPayments() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment[] payments = new Payment[3];
        payments[0] = new Payment("credit");
        payments[1] = new Payment("debit");
        payments[2] = new Payment("crypto");
        when(accountDAO.getPayments(user)).thenReturn(payments);

        // Invoke
        ResponseEntity<Payment[]> response = accountController.getPayments(token, user);

        // Analysis
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(payments, response.getBody());
    }

    // Tests deleteAccount () method to ensure correct status codes are
    // produced when the requested Account to be deleted does not exist
    @Test
    public void testGetPaymentsNotFound() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment[] payments = new Payment[3];
        payments[0] = new Payment("credit");
        payments[1] = new Payment("debit");
        payments[2] = new Payment("crypto");
        when(accountDAO.getPayments(user)).thenReturn(null);

        // Invoke
        ResponseEntity<Payment[]> response = accountController.getPayments(token, user);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    // Tests deleteAccount () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testGetPaymentsHandleException() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        doThrow(new IOException()).when(accountDAO).getPayments(user);

        // Invoke
        ResponseEntity<Payment[]> response = accountController.getPayments(token, user);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests addPayment() method to ensure correct status codes are
    // produced when adding a payment
    @Test
    public void testAddPayment() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment payment = new Payment("crypto");

        when(accountDAO.addToPayments(user, payment)).thenReturn(payment);

        // Invoke
        ResponseEntity<Payment> response = accountController.addPayment(token, user, payment);

        // Analysis
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(payment, response.getBody());
    }

    // Tests deleteAccount () method to ensure correct status codes are
    // produced when the requested Account to be deleted does not exist
    @Test
    public void testAddPaymentNotFound() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment payment = new Payment("crypto");

        when(accountDAO.addToPayments(user, payment)).thenReturn(null);

        // Invoke
        ResponseEntity<Payment> response = accountController.addPayment(token, user, payment);

        // Analysis
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    // Tests deleteAccount () method to ensure correct status codes are
    // produced when a server error occurs during method execution
    @Test
    public void testAddPaymentHandleException() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment payment = new Payment("crypto");

        doThrow(new IOException()).when(accountDAO).addToPayments(user, payment);

        // Invoke
        ResponseEntity<Payment> response = accountController.addPayment(token, user, payment);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    // Tests removePayment() method to ensure correct status codes are
    // produced when removing a payment
    @Test
    public void testRemovePayment() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment payment = new Payment("crypto");

        when(accountDAO.removeFromPayments(user, payment)).thenReturn(true);

        // Invoke
        ResponseEntity<Payment> response = accountController.removePayment(token, user, payment);

        // Analysis
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Tests removePayment() method to ensure correct status codes are
    // produced when removing a payment
    @Test
    public void testRemovePaymentNotFound() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment payment = new Payment("crypto");

        when(accountDAO.removeFromPayments(user, payment)).thenReturn(false);

        // Invoke
        ResponseEntity<Payment> response = accountController.removePayment(token, user, payment);

        // Analysis
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Tests removePayment() method to ensure correct status codes are
    // produced when removing a payment
    @Test
    public void testRemovePaymentHandleException() throws IOException {
        // Setup
        String user = "user";
        int token = Account.getToken(user);
        Payment payment = new Payment("crypto");

        doThrow(new IOException()).when(accountDAO).removeFromPayments(user, payment);

        // Invoke
        ResponseEntity<Payment> response = accountController.removePayment(token, user, payment);

        // Analysis
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
