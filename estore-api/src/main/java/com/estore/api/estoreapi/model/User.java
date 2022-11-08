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

    /**
     * Create a user with the given id and username
     * @param id The id of the User
     * @param userName The username of the User
     */
    public User( @JsonProperty("id") int id,@JsonProperty("UserName") String userName) {
        this.id = id;
        this.userName = userName;
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
     * Sets the username of the User
     * @param userName The username of the User
     */
    public void setUserName(String userName){this.userName = userName;}

    /**
     * Checks if the user is an admin by checking if the user has an id of 0 and a 
     * username of "Admin"
     * @return True if the user is admin
     *         False if the user is not an admin
     */
    @JsonIgnore
    public Boolean isAdmin(){return this.id == 0 && this.userName.equals("admin");}
    
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
        if(this.userName.toLowerCase().equals(otherUser.userName.toLowerCase())) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.userName.toLowerCase().hashCode();
    }
}
