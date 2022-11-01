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

        int expected_id = -1;
        String expected_userName = "admin";
        String expected_password = "admin";
        
        User user = new User(expected_id,expected_userName, expected_password);

        assertEquals(expected_id, user.getId());
        assertEquals(expected_userName, user.getUserName());
        assertEquals(expected_password, user.getPassword());
    }

    @Test
    public void testUserCtor(){

        int expected_id = 1;
        String expected_userName = "Zach";
        String expected_password = "ZachPass";
        
        User user = new User(expected_id,expected_userName, expected_password);

        assertEquals(expected_id, user.getId());
        assertEquals(expected_userName, user.getUserName());  
        assertEquals(expected_password, user.getPassword());  
    }
    @Test
    public void testToString(){
        int id = 1;
        String userName = "Zach";
        String passWord = "ZachPass";
        String expected_String = String.format(User.STRING_FORMAT, id, userName);

        User user = new User(id, userName, passWord);

        String actual_string = user.toString();

        assertEquals(expected_String, actual_string);
    }

    @Test
    public void testisAdmin(){
        int id = -1;
        String userName = "admin";
        String password = "admin";

        User user = new User(id, userName, password);

        assertEquals(true, user.isAdmin());
    }

    @Test
    public void testnotAdmin(){
        int id = 1;
        String userName = "Zach";
        String passWord = "ZachPass";
        
        User user = new User(id, userName, passWord);

        assertEquals(false, user.isAdmin());
    }

}
