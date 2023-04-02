package com.estore.api.estoreapi.persistance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.persistence.AccountFileDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

/**
 * Test the Account File DAO class
 * 
 * @author Adrian Marcellus
 */
@Tag("Persistence-tier")
public class AccountFileDAOTest {
    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAccounts = new Account[3];
        testAccounts[0] = new Account("I");
        testAccounts[1] = new Account("Dislike");
        testAccounts[2] = new Account("Angular");
        testAccounts[2].addPayment(new Payment("PayPal"));
        testAccounts[2].addPayment(new Payment("PayPal3"));
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Account[].class))
                .thenReturn(testAccounts);
        accountFileDAO = new AccountFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testLogIn() {
        String user = "Angular";
        Token token = assertDoesNotThrow(() -> accountFileDAO.logIn("Angular"),
        "Unexpected exception thrown");
        assertEquals(token.getToken(), Account.getToken(user));
    }

    @Test
    public void testDeleteAccount() throws IOException {
        String user = "Angular";
        int token = Account.getToken(user);
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount(token, user),
                            "Unexpected exception thrown");
        assertEquals(result,true);
    }

    @Test
    public void testCreateAccount() {
        String user = "Testing";
        Account result = assertDoesNotThrow(() -> accountFileDAO.createAccount(user),
                            "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals(result.getUserName(), user);
    }

    @Test
    public void testAddToPayments() {
        String user = "Angular";
        String type = "PayPal2";
        Payment payment = new Payment(type);
        Payment result = assertDoesNotThrow(()-> accountFileDAO.addToPayments(user, payment), "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals(result.getType(), type);

    }
    @Test
    public void testRemoveFromPayments() throws IOException{
        String user = "Angular";
        String type = "PayPal";
        Payment payment = new Payment(type);
        
        boolean result = assertDoesNotThrow(()-> accountFileDAO.removeFromPayments(user, payment), "Unexpected exception thrown");
        
        assert(result);
        Payment[] actual = accountFileDAO.getPayments(user);
        
        boolean contains = false;
        for(Payment p:actual){
            if(p.equals(payment)){
                contains = true;
            }
        }
        assertEquals(contains, false);
    }

    @Test
    public void testGetPayments() throws IOException{
        String user = "Angular";
        String type1 = "PayPal";
        String type2 = "PayPal3";
        Payment[] payments = {new Payment(type1), new Payment(type2)};

        Payment[] actual = assertDoesNotThrow(()-> accountFileDAO.getPayments(user), "Unexpected exception thrown");
        assertNotNull(actual);
        
        assertEquals(payments[0], actual[0]);
        assertEquals(payments[1], actual[1]);

    }
}


