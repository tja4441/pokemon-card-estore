package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * Before each test, create test Users with different ShoppingCarts
     * to use while testing.
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDao() throws IOException {
        mockInventoryDao = mock(InventoryDao.class);
        inventoryController = new InventoryController(mockInventoryDao);

        mockObjectMapper = mock(ObjectMapper.class);

        /** ArrayList of Product sets to be put in ShoppingCarts */
        testProductSets = new ArrayList<HashSet<Product>>();
        HashSet<Product> setOne = new HashSet<Product>();
        setOne.add( new Product(2,"Pikachu",10,100.00f));
        testProductSets.add( setOne );
        HashSet<Product> setTwo = setOne;
        setTwo.add( new Product(3, "Magikarp",1,24999.99f));
        testProductSets.add( setTwo );

        /** Array of ShoppingCarts for testing */
        testShoppingCarts = new ShoppingCart[3];
        testShoppingCarts[0] = new ShoppingCart();
        testShoppingCarts[1] = new ShoppingCart(testProductSets.get(0));
        testShoppingCarts[2] = new ShoppingCart(testProductSets.get(1));

        /** Array of Users for testing */
        testUsers = new User[6];
        testUsers[0] = new User(0, "admin", null);
        testUsers[1] = new User(1,"Tim",testShoppingCarts[0]);
        testUsers[2] = new User(2, "Zach",testShoppingCarts[1]);
        testUsers[3] = new User(3,"Daniel",testShoppingCarts[1]);
        testUsers[4] = new User(4, "Gabe",testShoppingCarts[2]);
        testUsers[5] = new User(5, "Jensen", testShoppingCarts[2]);

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
        assertEquals(users.length, testUsers.length);
        for(int i = 0; i < testUsers.length; ++i) {
            assertEquals(users[i], testUsers[i]);
        }
    }

    @Test
    public void createUser() throws IOException {
        // Setup
        User user = new User(6, "Bob", testShoppingCarts[0]);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDao.createUser(user),
                                    "Unexpected exception thrown: Bob");

         // Analyze
        assertNotNull(result);
        User actual = userFileDao.getUser(user.getUserName());
        assertEquals(actual.getUserName(),user.getUserName());
        assertEquals(actual.getShoppingCart(),user.getShoppingCart());
        assertEquals(actual.getId(),user.getId());
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper)
            .writeValue(any(File.class),any(User[].class));
        
        User user = new User(6, "Gerald",testShoppingCarts[1]);

        assertThrows(IOException.class,
                        () -> userFileDao.createUser(user),
                        "no IOException thrown");
    }

    @Test
    public void testGetUserNotFound() throws IOException {  // Fails; getUser cannot assume the user exists
        // Invoke
        User user = userFileDao.getUser("Joanne");

        // Analyze
        assertEquals(user,null);
    }

    @Test
    public void testCreateUserSameName() throws IOException {   // Fails; createUser cannot assume username is not already in use
        // Invoke
        User user = userFileDao.createUser(new User(6, "Gabriel",testShoppingCarts[0]));

        // Analyze
        assertNull(user);
    }

    @Test
    public void testCreateUserSpaceName() throws IOException {   // Fails; createUser cannot assume username is visible
        // Invoke
        User user = userFileDao.createUser(new User(6, "  ",testShoppingCarts[0]));

        // Analyze
        assertNull(user);
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
}

