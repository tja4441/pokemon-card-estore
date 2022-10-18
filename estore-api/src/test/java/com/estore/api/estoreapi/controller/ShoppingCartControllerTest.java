package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ShoppingCartDao;

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

public class ShoppingCartControllerTest {

    private ShoppingCartController shoppingCartController;
    private ShoppingCartDao mockShoppingCartDao;
    private InventoryController mockInventoryController;
    private UserController mockUserController;
    private User user;
    
    @BeforeEach
    public void setupShoppingCartController(){
        mockShoppingCartDao = mock(ShoppingCartDao.class);
        mockInventoryController = mock(InventoryController.class);
        shoppingCartController = new ShoppingCartController(mockShoppingCartDao, mockInventoryController); 
    
    }

    private static User createTestUser() {
        User user = new User(1, "Jeff");
        return user;
    }

    @Test
    public void testDeleteCart() throws IOException{
        
        when(mockShoppingCartDao.deleteCart(createTestUser().getId())).thenReturn(true);


        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteCart(createTestUser().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testDeleteCartNotFound() throws IOException{
        
        when(mockShoppingCartDao.deleteCart(1)).thenReturn(false);


        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteCart(5);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testDeleteCartHandleException() throws IOException{
        
        doThrow(new IOException()).when(mockShoppingCartDao).deleteCart(1);


        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteCart(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testUpdateCart() throws IOException{

        ShoppingCart cart = new ShoppingCart(3);
        
        when(mockShoppingCartDao.updateCart(cart)).thenReturn(cart);


        ResponseEntity<ShoppingCart> response = shoppingCartController.updateCart(cart);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());

    }

    @Test
    public void testUpdateCartConflict() throws IOException{

        ShoppingCart cart = new ShoppingCart(3);
        
        when(mockShoppingCartDao.updateCart(cart)).thenReturn(null);


        ResponseEntity<ShoppingCart> response = shoppingCartController.updateCart(cart);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

    }
    @Test
    public void testUpdateCartHandleException() throws IOException{
        ShoppingCart cart = new ShoppingCart(3);
        
        doThrow(new IOException()).when(mockShoppingCartDao).updateCart(cart);


        ResponseEntity<ShoppingCart> response = shoppingCartController.updateCart(cart);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    

    @Test
    public void testGetCart() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        
        when(mockShoppingCartDao.getCart(createTestUser().getId())).thenReturn(cart);


        ResponseEntity<ShoppingCart> response = shoppingCartController.getCart(cart.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());

    }

    @Test
    public void testGetCartNotFound() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        
        when(mockShoppingCartDao.getCart(createTestUser().getId())).thenReturn(null);


        ResponseEntity<ShoppingCart> response = shoppingCartController.getCart(cart.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    @Test
    public void testGetCartHandleException() throws IOException{
        ShoppingCart cart = new ShoppingCart(3);
        
        doThrow(new IOException()).when(mockShoppingCartDao).getCart(3);


        ResponseEntity<ShoppingCart> response = shoppingCartController.getCart(3);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }



    @Test
    public void testCreateCart() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        
        when(mockShoppingCartDao.createCart(cart)).thenReturn(cart);


        ResponseEntity<ShoppingCart> response = shoppingCartController.createCart(cart);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testCreateCartConflict() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        
        when(mockShoppingCartDao.createCart(cart)).thenReturn(null);


        ResponseEntity<ShoppingCart> response = shoppingCartController.createCart(cart);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateCartHandleException() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        
        doThrow(new IOException()).when(mockShoppingCartDao).createCart(cart);


        ResponseEntity<ShoppingCart> response = shoppingCartController.createCart(cart);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    
    }


    @Test
    public void testAddToCart() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        Product nProduct = new Product(5, "Apples", 10, 1.00f);
        
        when(mockShoppingCartDao.addToCart(1,nProduct)).thenReturn(cart);


        ResponseEntity<ShoppingCart> response = shoppingCartController.addToCart(1, nProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testAddToCartHandleException() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        Product nProduct = new Product(5, "Apples", 10, 1.00f);
        
        doThrow(new IOException()).when(mockShoppingCartDao).addToCart(1, nProduct);


        ResponseEntity<ShoppingCart> response = shoppingCartController.addToCart(1, nProduct);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    
    }


    @Test
    public void testDeleteFromCart() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        Product nProduct = new Product(5, "Apples", 10, 1.00f);
        
        when(mockShoppingCartDao.deleteFromCart(1,nProduct)).thenReturn(cart);


        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteFromCart(1, nProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testDeleteFromCartHandleException() throws IOException{

        ShoppingCart cart = new ShoppingCart(1);
        Product nProduct = new Product(5, "Apples", 10, 1.00f);
        
        doThrow(new IOException()).when(mockShoppingCartDao).deleteFromCart(1, nProduct);


        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteFromCart(1, nProduct);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    
    }

    
}
