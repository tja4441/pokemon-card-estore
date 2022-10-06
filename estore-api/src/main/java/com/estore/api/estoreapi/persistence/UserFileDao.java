package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.User;

/**
 * 
 */
public class UserFileDao implements UserDao {

    Map<Integer,User> users;            // provides a local cache of the User objects
    ObjectMapper objectMapper;          //Converts between Product objects and JSON text file formats
    String filename;                    //Filename to read/write
    private static final Logger LOG = Logger.getLogger(UserFileDao.class.getName());
    
    /**
     * 
     * @param filename
     * @param objectMapper
     * @throws IOException
     */
    public UserFileDao(@Value("${users.file}") String filename, 
                        ObjectMapper objectMapper )throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    /**
     * 
     * @return
     * @throws IOException
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of users
        User[] usersArray = objectMapper.readValue(new File(filename),User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : usersArray) {
            users.put(user.getId(),user);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String userName) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * 
     * @return
     */
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
