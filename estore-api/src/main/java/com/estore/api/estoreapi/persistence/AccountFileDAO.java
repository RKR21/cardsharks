package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.*;

/**
 * Implements the functionality for JSON file-based peristance for Products
 *
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author Adrian Marcellus
 */
@Component
public class AccountFileDAO implements AccountDAO {
    private static final Logger LOG = Logger.getLogger(AccountFileDAO.class.getName());
    Map<String, Account> accounts;
    private ObjectMapper objectMapper;
    private String filename;

    /**
     * Creates a Inventory File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AccountFileDAO(@Value("${accounts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private Account[] getAccountArray() {
        return accounts.values().toArray(new Account[accounts.size()]);
    }

    private boolean save() throws IOException {
        Account[] accountArray = getAccountArray();
        objectMapper.writeValue(new File(filename), accountArray);
        return true;
    }

    private boolean load() throws IOException {
        accounts = new HashMap<>();
        Account[] accountArray = objectMapper.readValue(new File(filename), Account[].class);
        for (Account account : accountArray)
            accounts.put(account.getUserName(), account);
        return true;
    }

    /**private Account getAccount(String userName) throws IOException {
        synchronized(accounts){
            return accounts.get(userName);
        }
    }*/

    public int logIn(String userName) throws IOException{
        synchronized(accounts) {
            if (accounts.containsKey(userName)){
                return Account.getToken(userName);
            }
            return 0;
        }
    } 

    @Override
    public Account createAccount(Account account) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(account.getUserName())){
                return null;
            }
            accounts.put(account.getUserName(), account);
            save();
            return account;
        }
    }

    @Override
    public boolean deleteAccount(String userName) throws IOException {
        synchronized(accounts) {
            if (!accounts.containsKey(userName))
                return false;
            accounts.remove(userName);
            return save();
        }
    }
}
