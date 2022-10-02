package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User
 * 
 * @author Team E
 */
public class User {

    static final String STRING_FORMAT = "User [id=%d, UserName=%s]"; // TODO add shopping cart to string format 


    @JsonProperty("id") private int id;
    @JsonProperty("UserName") private String userName;
     
    /**
     * Create a user with the given id and username
     * @param id The id of the User
     * @param userName The username of the User
     */
    public User( @JsonProperty("id") int id, 
                    @JsonProperty("UserName") String userName ){    //TODO shopping cart in the constructor once the shopping class is created
        this.id = id;
        this.userName = userName;
        //TODO Shopping Cart
    }

    /**
     * Create a Admin with an id of 0 and a username of "Admin"
     */
    public User(){      
        this.id = 0;
        this.userName = "Admin";
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
    public Boolean isAdmin(){return this.id == 0 && this.userName == "Admin";}

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {return String.format(STRING_FORMAT,id,userName);}

}
