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
public class CollectionFileDAO implements CollectionDAO{
    private static final Logger LOG = Logger.getLogger(CollectionFileDAO.class.getName());
    Map<Integer, Collection> collections;
    Map<Integer, Trade> trades;
    final int REQUEST_INDEX = 0;
    final int OFFER_INDEX = 1;
    private ObjectMapper objectMapper;
    private String filename;

    /**
     * Creates a Collection File Data Access Object
     *
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public CollectionFileDAO(@Value("${collections.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        this.trades = new HashMap<Integer, Trade>();
        load();
    }

    /**
     * Generates an array of {@linkplain Collection collections} from the tree map
     * 
     * @return  The array of {@link Collection collections}, may be empty
     */
    private Collection[] getCollectionArray() {
        return collections.values().toArray(new Collection[collections.size()]);
    }

    /**
     * Saves the {@linkplain Collection collections} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Collection collections} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Collection[] collectionArray = getCollectionArray();
        objectMapper.writeValue(new File(filename), collectionArray);
        return true;
    }

    /**
     * Loads {@linkplain Collection collections} from the JSON file into the map
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        collections = new HashMap<>();
        Collection[] collectionArray = objectMapper.readValue(new File(filename), Collection[].class);
        for (Collection collection : collectionArray)
        collections.put(collection.getToken(), collection);
        return true;
    }

    /**
     * CollectionDAO Override: creates a collection.
     */
    @Override
    public Collection createCollection(String userName) throws IOException{
        int token = Account.getToken(userName);
        if(collections.containsKey(token))
            return null;
        Collection newCollection = new Collection(userName);
        collections.put(token, newCollection);
        save();
        return newCollection; 
    }

    /**
     * CollectionDAO Override: deletes a collection.
     */
    @Override
    public boolean deleteCollection(int token) throws IOException{
        if(collections.remove(token) ==  null)
            return false;
        return save(); 
    }

    /**
     * CollectionDAO Override: adds to collection.
     */
    @Override
    public Product addToCollection(int token, Product product) throws IOException{
        synchronized(collections){
            if(!collections.containsKey(token))
                return null;
            collections.get(token).addToCollection(product);
            save();
            return product;
        }
    }

    /**
     * CollectionDAO Override: removes from collection.
     */
    @Override
    public boolean removeFromCollection(int token, int id) throws IOException{
        synchronized(collections){
            if(!collections.containsKey(token))
                return false;
            return collections.get(token).removeFromCollection(id) && save();
        }
    }

    /**
     * CollectionDAO Override: gets collection product array
     */
    @Override
    public Product[] getCollection(int token) throws IOException{
        synchronized(collections){
            if(!collections.containsKey(token))
                return null;
            return collections.get(token).getCollection();
        }
    }

    /**<---------TRADE FUNCTIONALITY-------------------------->*/

    /**
     * CollectionDAO Override: Makes a outgoing trade offer
     */
    @Override
    public Trade makeOffer(int token, 
        String userName, String otherName, 
        Product request, Product offer) throws IOException 
    {
        synchronized(collections){
            Collection owner = collections.get(token);
            int offerToken = Account.getToken(otherName);
            Collection other = collections.get(offerToken);
            if(other == null || !owner.contains(offer) || !other.contains(request))
                return null;
            Trade trade = new Trade(userName, otherName, offer, request);
            trades.put(offerToken, trade);
            return trade;
        } 
    }

    /**
     * CollectionDAO Override: Accepts a pending offer if it exists under a token
     */
    @Override
    public boolean acceptOffer(int token) throws IOException {
        synchronized(collections){
            Trade trade = trades.remove(token);
            if(trade == null)
                return false;
            int fromUserToken = Account.getToken(trade.getFromUser());
            int toUserToken = Account.getToken(trade.getToUser());
            Collection fromUser = collections.get(fromUserToken);
            Collection toUser = collections.get(toUserToken);
            if(fromUser == null || toUser ==  null)
                return false; 
            fromUser.addToCollection(trade.getRequest());
            fromUser.removeFromCollection(trade.getOffer().getId());
            toUser.addToCollection(trade.getOffer());
            toUser.removeFromCollection(trade.getRequest().getId());
            return true;
        }
    }

    /**
     * CollectionDAO Override: Rejects a pending offer if it exists under a token
     */
    @Override
    public boolean rejectOffer(int token) throws IOException {
        return trades.remove(token) != null;
    }

    /**
     * CollectionDAO Override: Gets a pending offer if it exists under a token
     */
    @Override
    public Trade getOffer(int token) throws IOException {
        return trades.get(token);
    }
}
