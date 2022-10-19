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

import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDao;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@RestController
@RequestMapping("home")
public class UserController extends Controller {
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDao userDao;
    //private ShoppingCartController shoppingCartController;

    /**
     * Creates a REST API controller to respond to requests
     * 
     * @param userDao The user data access object to perfom CRUD operations
     */
    public UserController(UserDao userDao/*, ShoppingCartController shoppingCartController*/){
        this.userDao = userDao;
        //this.shoppingCartController = shoppingCartController;
    }

    /**
     * Creates a {@linkplain User user} with the provided user
     * 
     * @param user - The {@link user user} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link User user} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> register(@RequestBody User user){
        LOG.info("POST /home" + user);
        try {
            if(userDao.getUser(user.getUserName()) != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
            User newUser = userDao.createUser(user);
            if (newUser != null) {
                //shoppingCartController.createCart(new ShoppingCart(newUser.getId()));
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

    /**
     * Gets a {@linkplain User user} with the provided username
     * 
     * @param User - The {@link User user} to login
     * 
     * @return ResponseEntity with {@link User user} object and HTTP status of CREATED
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{userName}")
    public ResponseEntity<User> login(@PathVariable String userName){
        LOG.info("GET /home/" + userName);
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
    
    /**
     * Gets all {@linkplain User user} 
     * 
     * @return ResponseEntity with an array {@link User user} objects and HTTP status of OK
     * ResponseEntity with HTTP status of Not_FOUND if {@link User user} object is null
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
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
