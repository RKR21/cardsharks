package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public Account createAccount(Account account) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(account.getUserName())){
                return null;
            }
            Account newAccount = new Account(
                account.getUserName(), new ArrayList<Product>(), new ArrayList<Product>());
            accounts.put(account.getUserName(), newAccount);
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

    public Product addToCart(String userName, Product product) throws IOException{
        synchronized(accounts){
            if(!accounts.containsKey(userName))
                return null;
            accounts.get(userName).addToCart(product);
            save();
            return product;
        }
    }

    public boolean removeFromCart(String userName, int index) throws IOException{
        synchronized(accounts){
            if(!accounts.containsKey(userName))
                return false;
            accounts.get(userName).removeFromCart(index);
            return save();
        }
    }

    public Product[] getCart(String userName) throws IOException{
        synchronized(accounts){
            if(!accounts.containsKey(userName))
                return null;
            return accounts.get(userName).getCart();
        }
    }

    public Product addToInventory(String userName, Product product) throws IOException{
        synchronized(accounts){
            if(!accounts.containsKey(userName))
                return null;
            accounts.get(userName).addToInventory(product);
            save();
            return product;
        }
    }

    public boolean removeFromInventory(String userName, int index) throws IOException{
        synchronized(accounts){
            if(!accounts.containsKey(userName))
                return false;
            accounts.get(userName).removeFromInventory(index);
            return save();
        }
    }

    public Product[] getInventory(String userName) throws IOException{
        synchronized(accounts){
            if(!accounts.containsKey(userName))
                return null;
            return accounts.get(userName).getInventory();
        }
    }
}
