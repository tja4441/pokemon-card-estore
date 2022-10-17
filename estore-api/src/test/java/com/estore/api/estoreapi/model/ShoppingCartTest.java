package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.controller.InventoryController;
import com.estore.api.estoreapi.persistence.InventoryDao;

@Tag("Model-Tier")
public class ShoppingCartTest {
    private InventoryController inventoryController;
    private InventoryDao mockInventoryDao;

    @BeforeEach
    public void setupInventoryController(){
        mockInventoryDao = mock(InventoryDao.class);
        inventoryController = new InventoryController(mockInventoryDao);
    }

    @Test
    public void testConstructor() {
        

        assertEquals(expectedSet, cart.getCartSet());
    }

    @Test
    public void testAddProduct() {
        HashSet<Product> expectedSet = new HashSet<Product>();
        
        ShoppingCart cart = new ShoppingCart(expectedSet,inventoryController);

        Product p = new Product(1, "Carrots", 50, 2.10f);
        expectedSet.add(p);
        cart.addToCart(p);

        assertEquals(expectedSet, cart.getCartSet());
    }

    @Test
    public void testRemoveProduct() {
        HashSet<Product> expectedSet = new HashSet<Product>();
        Product p = new Product(1, "Carrots", 50, 2.10f);
        expectedSet.add(p);
        
        ShoppingCart cart = new ShoppingCart(expectedSet,inventoryController);

        expectedSet.remove(p);
        cart.removeFromCart(p);

        assertEquals(expectedSet, cart.getCartSet());
    }

    @Test
    public void testUpdateProduct() {
        HashSet<Product> expectedSet = new HashSet<Product>();
        Product oldP = new Product(1, "Carrots", 50, 2.10f);
        Product newP = new Product(1, "Carrot", 25, 2f);
        expectedSet.add(newP);
        
        ShoppingCart cart = new ShoppingCart(inventoryController);
        cart.addToCart(oldP);
        cart.updateProductInCart(oldP, newP);

        assertEquals(expectedSet, cart.getCartSet());
    }

    @Test
    public void testSizeOf() {
        HashSet<Product> expectedSet = new HashSet<Product>();
        Product p = new Product(1, "Carrots", 50, 2.10f);
        expectedSet.add(p);
        
        ShoppingCart cart = new ShoppingCart(expectedSet,inventoryController);

        assertEquals(cart.size(), 1);
    }
}
