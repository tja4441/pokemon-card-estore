package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.User;

public interface UserDao {

    /**
     * Retrieves a user with the given name
     * @param userName The username of the user to get
     * @return a user object with the matching username
     * @throws IOException if an issue with storage
     */
    User getUser(String userName) throws IOException;

    /**
     * Creates a user and stores it
     * 
     * @param user The user to create
     * @return The user that was created
     * @throws IOException If an issue with storage
     */
    User createUser(User user) throws IOException;

    /**
     * 
     * @return
     * @throws IOException
     */
    User[] getUsers() throws IOException;
    
}
