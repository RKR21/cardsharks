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
 * Implements the functionality for JSON file-based peristance for Accounts
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

    /**
     * Generates an array of {@linkplain Account accounts} from the tree map
     * 
     * @return  The array of {@link Account accounts}, may be empty
     */
    private Account[] getAccountArray() {
        return accounts.values().toArray(new Account[accounts.size()]);
    }

    /**
     * Saves the {@linkplain Account accounts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Account accounts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Account[] accountArray = getAccountArray();
        objectMapper.writeValue(new File(filename), accountArray);
        return true;
    }

    /**
     * Loads {@linkplain Account accounts} from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        accounts = new HashMap<>();
        Account[] accountArray = objectMapper.readValue(new File(filename), Account[].class);
        for (Account account : accountArray)
            accounts.put(account.getUserName(), account);
        return true;
    }

    /**
     * AccountDAO Override: Log Into account to get a token.
     */
    @Override
    public Token logIn(String userName) throws IOException{
        synchronized(accounts) {
            if (accounts.containsKey(userName)){
                return new Token(Account.getToken(userName));
            }
            return new Token(0);
        }
    } 

    /**
     *  AccountDAO Override: Creates an account.
     */
    @Override
    public Account createAccount(String userName) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(userName)){
                return null;
            }
            Account account = new Account(userName);
            accounts.put(userName, account);
            save();
            return account;
        }
    }

    /**
     * AccountDAO Override: Deletes an account.
     */
    @Override
    public boolean deleteAccount(int token, String userName) throws IOException {
        synchronized(accounts) {
            if (!accounts.containsKey(userName) || token != Account.getToken(userName))
                return false;
            accounts.remove(userName);
            return save();
        }
    }

    /**
     * AccountDAO Override: adds a Payment.
     */
    public Payment addToPayments(String userName, Payment payment) throws IOException{
        synchronized(accounts) {
            if(!accounts.containsKey(userName))
                return null;

            Payment toReturn = accounts.get(userName).addPayment(payment);
            save();
            return toReturn;
        }
    }

    /**
     * AccountDAO Override: Deletes a Payment.
     */
    public boolean removeFromPayments(String userName, Payment payment) throws IOException{
        synchronized(accounts) {
            if(!accounts.containsKey(userName))
                return false;

            boolean toReturn = accounts.get(userName).removePayment(payment);
            save();
            return toReturn;
        }
    }

    /**
     * AccountDAO Override: gets payment array.
     */
    public Payment[] getPayments(String userName) throws IOException {
        synchronized(accounts) {
            if(!accounts.containsKey(userName))
                return null;

            Payment[] toReturn = accounts.get(userName).getPayments();
            save();
            return toReturn;
        }
    }
}
