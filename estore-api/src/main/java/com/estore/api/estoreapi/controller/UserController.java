package com.estore.api.estoreapi.controller;
/**
 * Handles the REST API requests for the User
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Team E
 */
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
        return null;
    }

    public ResponseEntity<User> getUser(){
        return null;
    }


        
    


}

