package com.estore.api.estoreapi.controller;
import org.springframework.http.HttpStatus;
/**
 * Handles the REST API requests for the User
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Team E
 */
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDao;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@RestController
@RequestMapping("login")
public class UserController extends Controller {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDao userDao;

    public UserController(UserDao userDao){
        this.userDao = userDao;
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user){
        LOG.info("POST /login" + user);
        try {
            User newUser = userDao.createUser(user);
            if (newUser != null) {
                return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUser(@PathVariable String userName){
        LOG.info("GET /login/" + userName);
        try {
            User user = userDao.getUser(userName);
            if(user != null){
                return new ResponseEntity<User>(user,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<User[]> getUsers(){
        
        try {
            User[] users = userDao.getUsers();
            if(users != null){
                return new ResponseEntity<User[]>(users,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } 
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



        
    


}

