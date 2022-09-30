package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("id") private int id;
    @JsonProperty("UserName") private String userName;
    @JsonProperty("Type") private String type;
    
    
    public User( @JsonProperty("id") int id, 
                    @JsonProperty("UserName") String userName, 
                    @JsonProperty("Type") String type  ){

        this.id = id;
        this.userName = userName;
        this.type = type;
    
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }




}
