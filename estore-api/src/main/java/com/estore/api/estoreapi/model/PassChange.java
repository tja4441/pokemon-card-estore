package com.estore.api.estoreapi.model;

public class PassChange {
    private String oldpass;
    private String newpass;
    
    public PassChange(String oldpass, String newpass) {
        this.oldpass = oldpass;
        this.newpass = newpass;
    }
    
    public String getOld() {
        return this.oldpass;
    }
    
    public String getNew() {
        return this.newpass;
    }
}
