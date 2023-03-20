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
        boolean result = assertDoesNotThrow(() -> accountFileDAO.deleteAccount(user),
                            "Unexpected exception thrown");
        assertEquals(result,true);
    }

    @Test
    public void testCreateAccount() {
        String user = "Testing";
        Account account = new Account(user);
        Account result = assertDoesNotThrow(() -> accountFileDAO.createAccount(account),
                            "Unexpected exception thrown");
        assertNotNull(result);
        assertEquals(result.getUserName(), account.getUserName());
    }
}


