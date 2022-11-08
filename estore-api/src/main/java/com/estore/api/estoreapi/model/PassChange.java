package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PassChange {
    private String oldPass;
    private String newPass;
    
    public PassChange(@JsonProperty("oldPass") String oldPass, @JsonProperty("newPass") String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
    }
    
    public String getOld() {
        return this.oldPass;
    }
    
    public String getNew() {
        return this.newPass;
    }
}
