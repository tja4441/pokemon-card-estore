package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
/**
 * The unit test suite for the User Class
 * 
 * @author Team E
 */
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class UserTest {

    @Test
    public void testAdminCtor(){

        int expected_id = 0;
        String expected_userName = "admin";
        
        User user = new User(expected_id,expected_userName);

        assertEquals(expected_id, user.getId());
        assertEquals(expected_userName, user.getUserName());
    }

    @Test
    public void testUserCtor(){

        int expected_id = 1;
        String expected_userName = "Zach";
        
        User user = new User(expected_id,expected_userName);

        assertEquals(expected_id, user.getId());
        assertEquals(expected_userName, user.getUserName());  
    }
    @Test
    public void testToString(){
        int id = 1;
        String userName = "Zach";
        String expected_String = String.format(User.STRING_FORMAT, id, userName);

        User user = new User(id, userName);

        String actual_string = user.toString();

        assertEquals(expected_String, actual_string);
    }

    @Test
    public void testisAdmin(){
        int id = 0;
        String userName = "admin";

        User user = new User(id, userName);

        assertEquals(true, user.isAdmin());
    }

    @Test
    public void testnotAdmin(){
        int id = 1;
        String userName = "Zach";
        User user = new User(id, userName);

        assertEquals(false, user.isAdmin());
    }

}
