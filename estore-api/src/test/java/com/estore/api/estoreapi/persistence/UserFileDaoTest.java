package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.PassChange;
import com.estore.api.estoreapi.model.Product;

/**
 * Test the User File DAO class
 * 
 * @author Timothy Avila
 */
@Tag("Persistence-tier")
public class UserFileDaoTest {
    UserFileDao userFileDao;
    User[] testUsers;
    ObjectMapper mockObjectMapper;
    ShoppingCart[] testShoppingCarts;
    ArrayList<HashSet<Product>> testProductSets;
    InventoryDao mockInventoryDao;
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static MessageDigest DIGEST;
    

    
    private byte[] hash(String string, String username) {
        DIGEST.update(username.getBytes(CHARSET));
        return DIGEST.digest(string.getBytes(CHARSET));
    }

    /**
     * Before each test, create test Users with different ShoppingCarts
     * to use while testing.
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDao() throws IOException {
        mockInventoryDao = mock(InventoryDao.class);

        mockObjectMapper = mock(ObjectMapper.class);

        try {
            UserFileDaoTest.DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {}

        /** Array of Users for testing */
        testUsers = new User[6];
        testUsers[0] = new User(-1, "admin", hash("admin", "admin"));
        testUsers[1] = new User(1,"Tim", hash("TimPass", "Tim"));
        testUsers[2] = new User(2, "Zach", hash("ZachPass", "Zach"));
        testUsers[3] = new User(3,"Daniel", hash("DanielPass", "Daniel"));
        testUsers[4] = new User(4, "Gabe", hash("GabePass", "Gabe"));
        testUsers[5] = new User(5, "Jensen", hash("JensenPass", "Jensen"));

        /** Behavior definitions for the mockObjectMapper */
        when(mockObjectMapper.readValue(new File("filename.txt"),User[].class)).thenReturn(testUsers);
        when(mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockObjectMapper);

        /** Create userFileDao to test with */
        userFileDao = new UserFileDao("filename.txt", mockObjectMapper);
    }

    @Test
    public void testGetUser() throws IOException {
        //Invoke
        User user = userFileDao.getUser("Daniel");

        //Analyze
        assertEquals(user, testUsers[3]);
    }

    @Test
    public void testGetUsers() throws IOException {
        //Invoke
        User[] users = userFileDao.getUsers();

        //Analyze
        assertEquals(testUsers.length, users.length);
        for(int i = 0; i < testUsers.length; ++i) {
            assertEquals(users[i], testUsers[i]);
        }
    }

    @Test
    public void createUser() throws IOException {
        // Setup
        User user = new User(6, "Bob", "BobPass");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDao.createUser(user),
                                    "Unexpected exception thrown: Bob");

         // Analyze
        assertNotNull(result);
        User actual = userFileDao.getUser(user.getUserName());
        assertEquals(actual.getUserName(),user.getUserName());
        assertEquals(actual.getId(),user.getId());
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper)
            .writeValue(any(File.class),any(User[].class));
        
        User user = new User(6, "Gerald", "GeraldPass");

        assertThrows(IOException.class,
                        () -> userFileDao.createUser(user),
                        "no IOException thrown");
    }

    @Test
    public void testGetUserNotFound() throws IOException {
        // Invoke
        User user = userFileDao.getUser("Joanne");

        // Analyze
        assertEquals(user,null);
    }

    @Test
    public void testCreateUserSameName() throws IOException { 
        // Invoke
        User user = userFileDao.createUser(new User(6, "Zach", "ZachPass"));

        // Analyze
        assertNull(user);
    }

    @Test
    public void testCreateUserSpaceName() throws IOException {   // Fails; createUser cannot assume username is visible
        // Invoke
        User user = userFileDao.createUser(new User(6, "  ", " "));

        // Analyze
        assertNull(user);
    }

    @Test
    public void testCreateUserNoPass() throws IOException {   // Fails; createUser cannot assume username is visible
        // Invoke
        User user = userFileDao.createUser(new User(6, "Jabe", ""));

        // Analyze
        assertNull(user);
    }

    @Test
    public void testCreateUserContainsSpaceName() throws IOException {   // Fails; createUser cannot assume username is visible
        // Invoke
        User user = userFileDao.createUser(new User(6, "Bob John", "password"));
        User user2 = userFileDao.createUser(new User(7, "Bo        b", "password"));

        // Analyze
        assertNull(user);
        assertNull(user2);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockObjectMapper);

        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("filename.txt"),User[].class);
        
        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new UserFileDao("filename.txt",mockObjectMapper),
                        "no IOException thrown");
    }

    @Test
    public void testCreateAdmin() throws IOException {
        // Setup
        User admin = new User(-2, "Gabemin", "AdminPass");

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDao.createAdmin(admin),
                                    "Unexpected exception thrown: Bob");

         // Analyze
        assertNotNull(result);
        User actual = userFileDao.getUser(admin.getUserName());
        assertEquals(actual.getUserName(),admin.getUserName());
        assertEquals(actual.getId(),admin.getId());
    }

    @Test
    public void testCreateAdminIOException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper)
            .writeValue(any(File.class),any(User[].class));
        
        User user = new User(-2, "Gerald", "GeraldPass");

        assertThrows(IOException.class,
                        () -> userFileDao.createAdmin(user),
                        "no IOException thrown");
    }

    @Test
    public void testCreateAdminSameName() throws IOException { 
        // Invoke
        User user = userFileDao.createAdmin(new User(-1, "admin", "admin"));

        // Analyze
        assertNull(user);
    }

    @Test
    public void testCreateAdminSpaceName() throws IOException {   // Fails; createUser cannot assume username is visible
        // Invoke
        User user = userFileDao.createAdmin(new User(-2, "  ", " "));

        // Analyze
        assertNull(user);
    }

    @Test
    public void testCreateAdminNoPass() throws IOException {   // Fails; createUser cannot assume username is visible
        // Invoke
        User user = userFileDao.createAdmin(new User(-2, "Jabe", ""));

        // Analyze
        assertNull(user);
    }

    @Test
    public void testCreateAdminContainsSpaceName() throws IOException {   // Fails; createUser cannot assume username is visible
        // Invoke
        User user = userFileDao.createAdmin(new User(6, "Bob John", "password"));
        User user2 = userFileDao.createAdmin(new User(7, "Bo        b", "password"));

        // Analyze
        assertNull(user);
        assertNull(user2);
    }

    @Test
    public void testValidatePassword() {
        String username = "Gabe";
        String password = "GabePass";
        User user = new User(4, username, hash(password, username));
        boolean result = userFileDao.validatePassword(user, password);
        assertTrue(result);
    }

    @Test
    public void testValidateBadPassword() {
        String username = "Gabe";
        String realPass = "GabePass";
        String wrongPass = "password";
        User user = new User(4, username, hash(realPass, username));
        boolean result = userFileDao.validatePassword(user, wrongPass);
        assertFalse(result);
    }

    @Test
    public void testChangePassword() throws IOException {
        int id = 4;
        String oldpass = "GabePass";
        String newpass = "gabepass";
        // Invoke
        PassChange changeform = new PassChange(oldpass, newpass);
        Boolean success = userFileDao.changePassword(id, changeform);

        // Analyze
        assertTrue(success);
    }
    
    @Test
    public void testChangeBadPassword() throws IOException {
        int id = 4;
        String wrongPass = "password";
        String newpass = "gabepass";
        // Invoke
        PassChange changeform = new PassChange(wrongPass, newpass);
        Boolean success = userFileDao.changePassword(id, changeform);

        // Analyze
        assertFalse(success);
    }
}

