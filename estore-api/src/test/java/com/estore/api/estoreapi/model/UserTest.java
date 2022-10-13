package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
/**
 * The unit test suite for the User Class
 * 
 * @author Team E
 */
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.controller.InventoryController;
import com.estore.api.estoreapi.persistence.InventoryDao;


@Tag("Model-tier")
public class UserTest {
    private InventoryController inventoryController;
    private InventoryDao mockInventoryDao;

    @BeforeEach
    public void setupInventoryController(){
        mockInventoryDao = mock(InventoryDao.class);
        inventoryController = new InventoryController(mockInventoryDao);
    }

    @Test
    public void testAdminCtor(){

        int expected_id = 0;
        String expected_userName = "admin";
        ShoppingCart expected_ShoppingCart = null;
        
        User user = new User(expected_id,expected_userName, null);

        assertEquals(expected_id, user.getId());
        assertEquals(expected_userName, user.getUserName());
        assertEquals(expected_ShoppingCart, user.getShoppingCart());  
    }

    @Test
    public void testUserCtor(){

        int expected_id = 1;
        String expected_userName = "Zach";
        ShoppingCart expected_ShoppingCart = new ShoppingCart(inventoryController);
        
        User user = new User(expected_id,expected_userName, new ShoppingCart(inventoryController));

        assertEquals(expected_id, user.getId());
        assertEquals(expected_userName, user.getUserName());
        assertEquals(expected_ShoppingCart, user.getShoppingCart());  
    }
    @Test
    public void testToString(){
        int id = 1;
        String userName = "Zach";
        ShoppingCart shoppingCart = new ShoppingCart(inventoryController);
        Product Product = new Product(1, "Carrots", 50, 2.10f);
        shoppingCart.add(Product);
        String expected_String = String.format(User.STRING_FORMAT, id, userName, shoppingCart);

        User user = new User(id, userName, shoppingCart);

        String actual_string = user.toString();

        assertEquals(expected_String, actual_string);
    }

    @Test
    public void testisAdmin(){
        int id = 0;
        String userName = "admin";

        User user = new User(id, userName, null);

        assertEquals(true, user.isAdmin());
    }

    @Test
    public void testnotAdmin(){
        int id = 1;
        String userName = "Zach";
        ShoppingCart shoppingCart = new ShoppingCart();
        Product Product = new Product(1, "Carrots", 50, 2.10f);
        shoppingCart.add(Product);
        User user = new User(id, userName, shoppingCart);

        assertEquals(false, user.isAdmin());
    }


    




    
}
