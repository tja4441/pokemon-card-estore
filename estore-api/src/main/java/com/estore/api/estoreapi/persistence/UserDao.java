package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.PassChange;
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
     * Creates an admin
     * @param user the username and password of the too be created admin
     * @return the created Admin
     * @throws IOException If it fails to edit storage
     */
    public User createAdmin(User user) throws IOException;

   /**
     * Retrieves all users in the inventory
     * @return An array of user objects, may be empty
     * @throws IOException if an issue with storage
     */
    User[] getUsers() throws IOException;

    /**
     * Attempts to change the user with the specified ids password.
     * If the old password that the user sends does not match the actual stored
     * password or a user does not exist at the given id it returns false otherwise true.
     * @param id the id of the user that you would like to change the password of
     * @param change A password change object that contains an old and new password
     * @return returns true on success and false on a failed opperation
     * @throws IOExeption throws exeption if there is an issue with changing persistant storage
     */
    public boolean changePassword(int id, PassChange change) throws IOException;

    /**
     * Compares a string password given when hashed to stored hashed password
     * @param user the user you are checking if the password matches
     * @param password the string password that has been sent
     * @return returns true if the password matches and false if it does not.
     */
    public boolean validatePassword(User user, String password);
}
