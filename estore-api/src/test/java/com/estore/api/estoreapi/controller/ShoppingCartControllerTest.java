package com.estore.api.estoreapi.controller;

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
    
    @BeforeEach
    public void setupShoppingCartController(){
        mockShoppingCartDao = mock(ShoppingCartDao.class);
        mockInventoryController = mock(InventoryController.class);
        shoppingCartController = new ShoppingCartController(mockShoppingCartDao, mockInventoryController); 
    }
    
}
