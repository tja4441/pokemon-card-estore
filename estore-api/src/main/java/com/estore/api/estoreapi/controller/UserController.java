package com.estore.api.estoreapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDao;

import java.util.logging.Logger;

@RestController
@RequestMapping("login")
public class UserController extends Controller {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDao userDao;

    public UserController(UserDao userDao){
        this.userDao = userDao;
    }

    public ResponseEntity<User> createUser(){

    }

    public ResponseEntity<User> getUser(){
        
    }

    
        
    }


}
