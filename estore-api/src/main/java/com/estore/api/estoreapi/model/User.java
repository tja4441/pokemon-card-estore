package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User
 * 
 * @author Team E
 */
public class User {

    static final String STRING_FORMAT = "User [id=%d, UserName=%s]"; 

    @JsonProperty("id") private int id;
    @JsonProperty("UserName") private String userName;
    private String password;
    @JsonProperty("HashedPass") private byte[] hashedPass;

    /**
     * Create a user with the given id and username
     * @param id The id of the User
     * @param userName The username of the User
     * @param password The user's password
     */
    public User( @JsonProperty("id") int id, @JsonProperty("UserName") String userName, @JsonProperty("Password") String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.hashedPass = null;
    }
    public User(int id, String userName, byte[] hashedPass) {
        this.id = id;
        this.userName = userName;
        this.hashedPass = hashedPass;
        this.password = "";
    }

    /**
     * Retrieves the id of the User
     * @return The id of the User
     */
    public int getId() {return id;}

    /**
     * Retrieves the username of the User
     * @return The username of the User
     */
    public String getUserName() {return userName;}

    /**
     * Retrieves the password of the User
     * @return The password of the User
     */
    @JsonIgnore
    public String getPassword() {return password;}

    /**
     * Retrieves hashed password
     * @return returns the users hashed password
     */
    public byte[] getHashedPass() {return hashedPass;}

    /**
     * Sets the username of the User
     * @param userName The username of the User
     */
    public void setUserName(String userName) {this.userName = userName;}

    /**
     * Sets the hashed password of the User
     * @param password The password of the User
     */
    public void setHashPass(byte[] password) {this.hashedPass = password;}

    /**
     * Checks if the user is an admin by checking if the user has an id of 0 and a 
     * username of "Admin"
     * @return True if the user is admin
     *         False if the user is not an admin
     */
    @JsonIgnore
    public Boolean isAdmin(){return this.id < 0;}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {return String.format(STRING_FORMAT,id,userName);}

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof User)) {
            return false;
        }
        User otherUser = (User) other;
        return this.userName.equals(otherUser.userName);
    }

    @Override
    public int hashCode() {
        return this.userName.toLowerCase().hashCode();
    }
}
