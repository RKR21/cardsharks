package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;

/**
 * Implements the functionality for JSON file-based peristance for Products
 *
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 *
 * @author SWEN Faculty
 * @author Adrian Marcellus
 */
@Component
public class InventoryFileDAO implements InventoryDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<Integer,Product> products;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    /**
     * Creates a Inventory File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public InventoryFileDAO(@Value("${products.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new {@linkplain Product products}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map
     * 
     * @return  The array of {@link Product products}, may be empty
     */
    private Product[] getProductArray() {
        return getProductArray(null);
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map for any
     * {@linkplain Product products} that contains the text specified by containsText
     * If containsText is null, the array contains all of the {@linkplain Product products}
     * in the tree map
     * 
     * @return  The array of {@link Product products}, may be empty
     */
    private Product[] getProductArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * Checks all existing {@linkplain Product products} for an exact match with the name string. 
     * Returns ture if found false otherwise
     * 
     * @param name String to be checked for
     * 
     * @return True if {@link Product product} with exact name was found false otherwise.
     */
    private boolean nameExists(String name){
        for (Product product : products.values()) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Product products} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductArray();
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    /**
     * Loads {@linkplain Product products} from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;
        Product[] productArray = objectMapper.readValue(new File(filename),Product[].class);
        for (Product product : productArray) {
            products.put(product.getId(),product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        ++nextId;
        return true;
    }

    /**
     * Retrives an array of all {@link Product products}
     * 
     * @return an array of {@link Product product} objects
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public Product[] getProducts() throws IOException{
        synchronized(products) {
            return getProductArray();
        }
    }

    /**
     * Retrives an array of all {@link Product products} that contain a substring
     * 
     * @param String substring to search for
     * 
     * @return an array of {@link Product product} objects that contain the substring in their display name
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized(products) {
            return getProductArray(containsText);
        }
    }

    /**
     * Retrives the {@link Product product} with the given id
     * 
     * @param id id of the {@link Product product} to be returned
     * 
     * @return the {@link Product product} object with the id given
     * null if no {@link Product product} with the given id exists
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public Product getProduct(int id) {
        synchronized(products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    /**
     * Adds a new {@link Product product} to the database
     * 
     * @param product {@link Product product} object to be added 
     * id is ignored and handled seperatly to maintain consistincy.
     * 
     * @return new {@link Product product} object or null if unsuccessful
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            if (nameExists(product.getName())){
                return null;
            }
            Product newProduct = new Product(nextId(), product.getName(), product.getPrice(), product.getQuantity());
            products.put(newProduct.getId(),newProduct);
            save();
            return newProduct;
        }
    }

   /**
     * updates an existing {@link Product product} 
     * 
     * @param product new {@link Product product} object
     * 
     * @return the {@link Product product} object that was updated
     * or null if no object was found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getId()) == false)
                return null;
            products.put(product.getId(),product);
            save();
            return product;
        }
    }

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product}
     * 
     * @return true if the {@link Product product} was deleted
     * false if product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized(products) {
            if (products.containsKey(id)) {
                products.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
