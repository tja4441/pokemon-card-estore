package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;

@Component
public class UserFileDao implements UserDao {

    private Map<Integer,User> users;            // provides a local cache of the User objects
    private ObjectMapper objectMapper;          //Converts between Product objects and JSON text file formats
    private String filename;                    //Filename to read/write
    private static final Logger LOG = Logger.getLogger(UserFileDao.class.getName());
    
    /**
     * Creates a User File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDao(@Value("${users.file}") String filename, 
                        ObjectMapper objectMapper )throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        
        load();
    }

   /**
     * Loads {@linkplain User user} from the JSON file into the map
     * 
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of users
        User[] usersArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : usersArray) {
            users.put(user.getId(),user);
        }
        init();
        return true;
    }
    /**
     * Checks if the file contains {@linkplain User user} admin, intilizalizes it it doesnt
     *  with an id of 0 and an empty cart
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private void init() throws IOException{
        users.put(0,new User(0,"admin", null));
        save();
    }
    /**
     * Saves the {@linkplain User user} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link User user} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        objectMapper.writeValue(new File(filename),userArray);
        return true;
    }

     /**
     * Generates an array of {@linkplain User user} from the tree map
     * 
     * @return  The array of {@link User user}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of {@linkplain User user} from the tree map for any
     * {@linkplain User user} that contains the text specified by containsText
     * 
     * If containsText is null, the array contains all of the {@linkplain User user}
     * in the tree map
     * 
     * @return  The array of {@link User user}, may be empty
     */
    private User[] getUsersArray(String containsText) { 
        ArrayList<User> usersArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getUserName().contains(containsText)) {
                usersArrayList.add(user);
            }
        }

        User[] usersArray = new User[usersArrayList.size()];
        usersArrayList.toArray(usersArray);
        return usersArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String userName) throws IOException {
        synchronized(users){
            if(users.size() != 0){
                User[] user = getUsersArray(userName);
                return user[0];
            }else{
                return null;
            }
         }
            
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized(users) {
            User newUser = new User(nextID(),user.getUserName(),new ShoppingCart());
            for (User other : users.values()) {
                if(user.equals(other)){
                    return null;
                }
            }
            users.put(newUser.getId(), newUser);
            save();
            return newUser;

        }
    }


     /**
     * *{@inheritDoc}
     */
    @Override
    public User[] getUsers() throws IOException {
        synchronized(users){
            return getUsersArray();
        }
    }
   
    private int nextID() {
        synchronized(users) {
            int i = 1;
            while(users.containsKey(i)) {
                i++;
            }
            return i;
        }
    }
}
