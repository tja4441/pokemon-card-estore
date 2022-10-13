package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.InventoryDao;
import com.estore.api.estoreapi.persistence.UserDao;

@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDao mockUserDao;

    private static User createTestUser() {
        Product testCard = new Product(1, "Charizard", 3, 2.00f);
        ShoppingCart cart = new ShoppingCart();
        cart.add(testCard);
        User user = new User(1, "Jeff", cart);
        return user;
    }

    @BeforeEach
    public void setupUserController(){
        mockUserDao = mock(UserDao.class);
        userController = new UserController(mockUserDao);
        User testUser = createTestUser();
    }

    /**
     * 
     * @throws IOException should not actually ever throw this exeption
     */
    @Test
    public void testRegisterUser() throws IOException{
        User user = createTestUser();
        when(mockUserDao.createUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.register(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    
    @Test
    public void testRegisterUserConflict() {
        User user = null;
        ResponseEntity<User> response = userController.register(user);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testRegisterUserHandlesException() throws IOException {
        User user = createTestUser();
        doThrow(new IOException()).when(mockUserDao).createUser(user);
        ResponseEntity<User> response = userController.register(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testLogin() throws IOException{
        User user = createTestUser();
        when(mockUserDao.getUser(user.getUserName())).thenReturn(user);
        ResponseEntity<User> response = userController.login(user.getUserName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testLoginNotFound() throws IOException{
        String name = "Jeff";
        when(mockUserDao.getUser(name)).thenReturn(null);
        ResponseEntity<User> response = userController.login(name);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * mimics trying to login as a non-existant user
     * @throws IOException
     */
    @Test
    public void testLoginHandlesException() throws IOException {
        String name = "Jeff";
        doThrow(new IOException()).when(mockUserDao).getUser(name);
        ResponseEntity<User> response = userController.login(name);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void getUsers() throws IOException{
        User[] users = {createTestUser(), new User(5, "Mary", null)};
        when(mockUserDao.getUsers()).thenReturn(users);
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void getUsersNotFound() throws IOException{
        when(mockUserDao.getUsers()).thenReturn(null);
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * mimics trying to login as a non-existant user
     * @throws IOException
     */
    @Test
    public void getUsersHandlesException() throws IOException {
        doThrow(new IOException()).when(mockUserDao).getUsers();
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
