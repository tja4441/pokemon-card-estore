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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;

import com.estore.api.estoreapi.controller.InventoryController;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Test the ShoppingCartFileDAO class
 * 
 * @author Timothy Avila
 */
@Tag("Persistence-tier")
public class ShoppingCartFileDaoTest {
    ShoppingCartFileDao shoppingCartFileDao;
    ShoppingCart[] testShoppingCarts;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;
    InventoryController mockInventoryController;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupShoppingCartFileDao() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        mockInventoryController = mock(InventoryController.class);

        testProducts = new Product[1];
        testProducts[0] = new Product(2,"Greninja",20,200.00f);

        ShoppingCart testShoppingCart = new ShoppingCart(1);
        testShoppingCart.addToCart(new Product(1, "Greninja", 20, 200.00f));
        testShoppingCarts = new ShoppingCart[1];
        testShoppingCarts[0] = testShoppingCart;
        
        when(mockObjectMapper.readValue(new File("Charmander_Is_Better.txt"),Product[].class)).thenReturn(testProducts);
        when(mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockObjectMapper);

        when(mockObjectMapper.readValue(new File("filename.txt"),ShoppingCart[].class)).thenReturn(testShoppingCarts);
        when(mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockObjectMapper);
        shoppingCartFileDao = new ShoppingCartFileDao("filename.txt", mockObjectMapper);
    }

    @Test
    public void testUpdateCart() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart(1);

        // Invoke
        ShoppingCart result = assertDoesNotThrow(() -> shoppingCartFileDao.updateCart(cart),
                                                            "Unexpected exception was thrown");

        // Analyze
        assertNotNull(result);
        ShoppingCart actual = shoppingCartFileDao.getCart(1);
        assertEquals(actual, cart);
    }

    @Test
    public void testGetCart() {
        // Invoke
        ShoppingCart cart = shoppingCartFileDao.getCart(1);

        // Analyze
        assertEquals(testShoppingCarts[0], cart);
    }

    @Test
    public void testCreateCart() throws IOException {
        // Invoke
        ShoppingCart cart = shoppingCartFileDao.createCart(new ShoppingCart(2));
        // Analyze
        assertNotNull(cart);
        ShoppingCart actual = new ShoppingCart(2);
        assertEquals(actual.getId(), cart.getId());
        assertEquals(actual.getContents(), cart.getContents());
        assertEquals(actual.GetTotalPrice(), cart.GetTotalPrice());
    }

    @Test
    public void testDeleteCart() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> shoppingCartFileDao.deleteCart(1),
                                                "Unexpected exception was thrown.");

        // Analyze
        assertEquals(result, true);
        assertEquals(shoppingCartFileDao.carts.size(), testShoppingCarts.length - 1);
    }

    @Test
    public void testAddToCart() throws IOException {
        // Invoke
        ShoppingCart cart = new ShoppingCart(1);
        cart = shoppingCartFileDao.addToCart(1, new Product(1, "Greninja", 20, 200.00f));
        ShoppingCart nullResult = shoppingCartFileDao.addToCart(2, new Product(1, "Greninja", 20, 200.00f));
        // Analyze
        assertEquals(testShoppingCarts[0], cart);
        assertNull(nullResult);
    }

    @Test
    public void testDeleteFromCart() throws IOException {
        // Invoke
        ShoppingCart cart = testShoppingCarts[0];
        cart = shoppingCartFileDao.deleteFromCart(1, new Product(1, "Greninja", 20, 200.00f));
        ShoppingCart nullResult = shoppingCartFileDao.deleteFromCart(2, new Product(1, "Greninja", 20, 200.00f));
        // Analyze
        assertEquals(new ShoppingCart(1), cart);
        assertNull(nullResult);
    }

    @Test
    public void testRefreshCart() throws IOException {
        // Invoke
        ShoppingCart cart1 = shoppingCartFileDao.createCart(new ShoppingCart(2));
        shoppingCartFileDao.addToCart(2, new Product(1, "Greninja", 25, 149.99f));
        shoppingCartFileDao.refreshCart(2, mockInventoryController);
        ShoppingCart cart2 = shoppingCartFileDao.createCart(new ShoppingCart(3));
        shoppingCartFileDao.addToCart(3, new Product(2, "Victini", 5, 29.50f));
        shoppingCartFileDao.updateCart(cart2);
        // Analyze
        assertEquals(cart1, testShoppingCarts[0]);
        assertEquals(cart2, new ShoppingCart(3));
    }

    @Test
    public void testCheckout() {
    }


}
