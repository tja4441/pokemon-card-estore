package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.PassChange;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDao;

@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDao mockUserDao;
    private ShoppingCartController mockShoppingCartController;
    private StatisticsController mockStatisticsController;

    @BeforeEach
    public void setupUserController(){
        mockUserDao = mock(UserDao.class);
        mockShoppingCartController = mock(ShoppingCartController.class);
        mockStatisticsController = mock(StatisticsController.class);
        userController = new UserController(mockUserDao, mockShoppingCartController, mockStatisticsController);
    }

    
    private static User createTestUser() {
        User user = new User(1, "Jeff", "password");
        return user;
    }
    
    private static User createTestAdmin() {
        User user = new User(-2, "John", "password");
        return user;
    }

    @Test
    public void testRegisterUser() throws IOException{
        User user = createTestUser();
        when(mockUserDao.createUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.register(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    
    @Test
    public void testRegisterNullUser() {
        User user = null;
        ResponseEntity<User> response = userController.register(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    public void testRegisterNoUsername() {
        User user = new User(1, "", "");
        ResponseEntity<User> response = userController.register(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testRegisterUserAlreadyExists() throws IOException {
        User user = createTestUser();
       when(mockUserDao.getUser(user.getUserName())).thenReturn(user);
        ResponseEntity<User> response = userController.register(user);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testRegisterUserConflict() throws IOException {
        User user = createTestUser();
       when(mockUserDao.createUser(user)).thenReturn(null);
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
    public void testCreateAdmin() throws IOException {
        User admin = createTestAdmin();
        when(mockUserDao.createAdmin(admin)).thenReturn(admin);
        ResponseEntity<User> response = userController.createAdmin(admin);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(admin, response.getBody());
    }
    
    @Test
    public void testCreateNullAdmin() {
        User admin = null;
        ResponseEntity<User> response = userController.createAdmin(admin);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateAdminNoUsername() {
        User admin = new User(-2, "", "");
        ResponseEntity<User> response = userController.createAdmin(admin);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCreateAdminHandlesException() throws IOException {
        User admin = createTestAdmin();
        doThrow(new IOException()).when(mockUserDao).createAdmin(admin);
        ResponseEntity<User> response = userController.createAdmin(admin);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testLogin() throws IOException{
        User user = createTestUser();
        when(mockUserDao.getUser(user.getUserName())).thenReturn(user);
        when(mockUserDao.validatePassword(user, user.getPassword())).thenReturn(true);
        ResponseEntity<User> response = userController.login(user.getUserName(), user.getPassword());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    
    @Test
    public void testLoginBadPassword() throws IOException{
        User user = createTestUser();
        when(mockUserDao.getUser(user.getUserName())).thenReturn(user);
        when(mockUserDao.validatePassword(user, user.getPassword())).thenReturn(false);
        ResponseEntity<User> response = userController.login(user.getUserName(), user.getPassword());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testLoginFails() throws IOException{
        User user = createTestUser();
        when(mockUserDao.getUser(user.getUserName())).thenReturn(user);
        when(mockUserDao.validatePassword(user, user.getPassword())).thenReturn(false);
        ResponseEntity<User> response = userController.login(user.getUserName(), user.getPassword());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testLoginNotFound() throws IOException{
        String name = "Jeff";
        String pass = "pass";
        when(mockUserDao.getUser(name)).thenReturn(null);
        ResponseEntity<User> response = userController.login(name, pass);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testLoginHandlesException() throws IOException {
        String name = "Jeff";
        String pass = "pass";
        doThrow(new IOException()).when(mockUserDao).getUser(name);
        ResponseEntity<User> response = userController.login(name, pass);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void getUsers() throws IOException{
        User[] users = {createTestUser(), new User(5, "Mary", "password")};
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

    @Test
    public void getUsersHandlesException() throws IOException {
        doThrow(new IOException()).when(mockUserDao).getUsers();
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testChangePassword() throws IOException {
        User user = createTestUser();
        PassChange form = new PassChange("old", "new");
        when(mockUserDao.changePassword(user.getId(), form)).thenReturn(true);
        ResponseEntity<Boolean> response = userController.changePassword(user.getId(), form);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }
    
    @Test
    public void testChangePasswordFailed() throws IOException {
        User user = createTestUser();
        PassChange form = new PassChange("old", "new");
        when(mockUserDao.changePassword(user.getId(), form)).thenReturn(false);
        ResponseEntity<Boolean> response = userController.changePassword(user.getId(), form);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(false, response.getBody());
    }
    
    @Test
    public void changePasswordHandlesException() throws IOException {
        User user = createTestUser();
        PassChange form = new PassChange("old", "new");
        doThrow(new IOException()).when(mockUserDao).changePassword(user.getId(), form);
        ResponseEntity<Boolean> response = userController.changePassword(user.getId(), form);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
