package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
    ObjectMapper mockInvObjectMapper;
    ObjectMapper mockObjectMapper;
    InventoryFileDao mockInventoryFileDao;
    InventoryController inventoryController;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupShoppingCartFileDao() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        mockInvObjectMapper = mock(ObjectMapper.class);
        mockInventoryFileDao = mock(InventoryFileDao.class);
        inventoryController = new InventoryController(mockInventoryFileDao);

        testShoppingCarts = new ShoppingCart[3];
        testShoppingCarts[0] = new ShoppingCart(1);
        testShoppingCarts[1] = new ShoppingCart(2);
        testShoppingCarts[2] = new ShoppingCart(3);

        testProducts = new Product[5];
        testProducts[0] = new Product(2,"Pikachu",4,100.00f);
        testProducts[1] = new Product(3, "Bulbasaur",5,2.50f);
        testProducts[2] = new Product(4,"Squirtle",4,1.00f);
        testProducts[3] = new Product(5, "Charmander",3,50.05f);
        testProducts[4] = new Product(7, "Clefairy", 10, 2.00f);

        when(mockInvObjectMapper.readValue(new File("Charmander_Is_Better.txt"),Product[].class)).thenReturn(testProducts);
        when(mockInvObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockInvObjectMapper);
        inventoryController = new InventoryController(mockInventoryFileDao);

        when(mockObjectMapper.readValue(new File("Charmander_Is_Better.txt"),ShoppingCart[].class)).thenReturn(testShoppingCarts);
        when(mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockObjectMapper);
        shoppingCartFileDao = new ShoppingCartFileDao("Charmander_Is_Better.txt",mockObjectMapper);
    }

    @Test
    public void testUpdateCart() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart(1);
        cart.addToCart(testProducts[1]);

        // Invoke
        ShoppingCart result = assertDoesNotThrow(() -> shoppingCartFileDao.updateCart(cart),
                                                            "Unexpected exception was thrown");

        // Analyze
        assertNotNull(result);
        ShoppingCart actual = shoppingCartFileDao.getCart(cart.getId());
        assertEquals(actual, cart);
    }

    @Test
    public void testUpdateCartNotFound() throws IOException {
        // Setup
        ShoppingCart cart = new ShoppingCart(5);
        cart.addToCart(testProducts[1]);

        // Invoke
        ShoppingCart result = assertDoesNotThrow(() -> shoppingCartFileDao.updateCart(cart),
                                                            "Unexpected exception was thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testGetCart() {
        // Invoke
        ShoppingCart cart = shoppingCartFileDao.getCart(1);

        // Analyze
        assertEquals(testShoppingCarts[0], cart);
    }

    @Test
    public void testGetCartNotFound() {
        // Invoke
        ShoppingCart cart = shoppingCartFileDao.getCart(4);

        // Analyze
        assertNull(cart);
    }

    @Test
    public void testCreateCart() throws IOException {
        // Invoke
        ShoppingCart cart = new ShoppingCart(4);

        ShoppingCart result = assertDoesNotThrow(() -> shoppingCartFileDao.createCart(4),
                                    "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);

        ShoppingCart actual = new ShoppingCart(4);
        assertEquals(actual.getId(), cart.getId());
        assertEquals(actual.getContents(), cart.getContents());
        assertEquals(actual.GetTotalPrice(), cart.GetTotalPrice());
    }

    @Test
    public void testCreateCartSame() throws IOException {
        // Invoke
        ShoppingCart result = assertDoesNotThrow(() -> shoppingCartFileDao.createCart(3),
                                    "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testDeleteCart() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> shoppingCartFileDao.deleteCart(1),
                                                "Unexpected exception was thrown.");

        // Analyze
        assertEquals(true, result);
        assertEquals(shoppingCartFileDao.carts.size(), testShoppingCarts.length - 1);
    }

    @Test
    public void testDeleteCartNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> shoppingCartFileDao.deleteCart(4),
                                                "Unexpected exception was thrown.");

        // Analyze
        assertEquals(false, result);
    }

    @Test
    public void testAddToCart() throws IOException {
        // Invoke
        ShoppingCart expectedCart = testShoppingCarts[0];
        expectedCart.getContents().add(testProducts[1]);

        ShoppingCart cart = shoppingCartFileDao.addToCart(1, testProducts[1]);
        ShoppingCart nullResult = shoppingCartFileDao.addToCart(4, testProducts[1]);

        // Analyze
        assertEquals(expectedCart, cart);
        assertNull(nullResult);
    }

    @Test
    public void testAddToCartIncrement() throws IOException {
        // Invoke
        testShoppingCarts[0].getContents().add(new Product(7, "Clefairy", 1, 2.00f));
        ShoppingCart expectedCart = testShoppingCarts[0];
        expectedCart.getContents().add(new Product(7, "Clefairy", 2, 2.00f));

        ShoppingCart cart = shoppingCartFileDao.addToCart(1, testProducts[4]);
        ShoppingCart nullResult = shoppingCartFileDao.addToCart(4, testProducts[1]);

        // Analyze
        assertEquals(expectedCart, cart);
        assertNull(nullResult);
    }

    @Test
    public void testDeleteFromCartMultiple() throws IOException {
        // Invoke
        testShoppingCarts[1].getContents().add(testProducts[0]);
        testShoppingCarts[1].calculateTotalPrice();

        ShoppingCart expectedCart = new ShoppingCart(2);
        expectedCart.getContents().add(new Product(2,"Pikachu",3,100.00f));
        expectedCart.calculateTotalPrice();

        ShoppingCart cart = shoppingCartFileDao.deleteFromCart(2, testProducts[0]);
        ShoppingCart nullResult = shoppingCartFileDao.deleteFromCart(4, testProducts[1]);

        // Analyze
        assertEquals(expectedCart, cart);
        assertNull(nullResult);
    }

    @Test
    public void testDeleteFromCartSingle() throws IOException {
        // Invoke
        testShoppingCarts[1].getContents().add(new Product(2,"Pikachu",1,100.00f));
        testShoppingCarts[1].calculateTotalPrice();

        ShoppingCart expectedCart = new ShoppingCart(2);

        ShoppingCart cart = shoppingCartFileDao.deleteFromCart(2, testProducts[0]);
        ShoppingCart nullResult = shoppingCartFileDao.deleteFromCart(4, testProducts[1]);

        // Analyze
        assertEquals(expectedCart, cart);
        assertNull(nullResult);
    }

    @Test
    public void testDeleteFromCartProductNotFound() throws IOException {
        // Invoke
        testShoppingCarts[1].getContents().add(new Product(2,"Pikachu",1,100.00f));
        testShoppingCarts[1].calculateTotalPrice();

        ShoppingCart expectedCart = null;

        ShoppingCart nullCart = shoppingCartFileDao.deleteFromCart(2, new Product(10,"Pichu",10,100.00f));

        // Analyze
        assertEquals(expectedCart, nullCart);
    }

    @Test
    public void testRefreshCart() throws IOException {
        // Invoke
        when(mockInventoryFileDao.getProduct(2)).thenReturn(testProducts[0]);
        when(mockInventoryFileDao.getProduct(3)).thenReturn(testProducts[1]);
        when(mockInventoryFileDao.getProduct(4)).thenReturn(testProducts[2]);
        when(mockInventoryFileDao.getProduct(5)).thenReturn(testProducts[3]);
        when(mockInventoryFileDao.getProduct(6)).thenReturn(null);

        ShoppingCart expectedCart = new ShoppingCart(3);
        expectedCart.getContents().add(testProducts[0]);

        ShoppingCart cart = testShoppingCarts[2];
        shoppingCartFileDao.addToCart(3, testProducts[0]);
        shoppingCartFileDao.addToCart(3, new Product(6, "Greninja", 25, 149.99f));
        shoppingCartFileDao.refreshCart(3, inventoryController);

        // Analyze
        assertEquals(expectedCart.getContents(), cart.getContents());
    }

    @Test
    public void testRefreshCartInventoryLess() throws IOException {
        // Invoke
        when(mockInventoryFileDao.getProduct(2)).thenReturn(testProducts[0]);
        when(mockInventoryFileDao.getProduct(3)).thenReturn(testProducts[1]);
        when(mockInventoryFileDao.getProduct(4)).thenReturn(testProducts[2]);
        when(mockInventoryFileDao.getProduct(5)).thenReturn(testProducts[3]);
        when(mockInventoryFileDao.getProduct(6)).thenReturn(null);
        when(mockInventoryFileDao.getProduct(7)).thenReturn(testProducts[4]);

        ShoppingCart expectedCart = new ShoppingCart(3);
        expectedCart.getContents().add(testProducts[4]);

        ShoppingCart cart = testShoppingCarts[2];
        cart.getContents().add(new Product(7, "Clefairy", 20, 2.00f));
        cart.calculateTotalPrice();
        shoppingCartFileDao.refreshCart(3, inventoryController);

        // Analyze
        assertEquals(expectedCart.getContents(), cart.getContents());
    }

    @Test
    public void testRefreshCartPriceDif() throws IOException {
        // Invoke
        when(mockInventoryFileDao.getProduct(2)).thenReturn(testProducts[0]);
        when(mockInventoryFileDao.getProduct(3)).thenReturn(testProducts[1]);
        when(mockInventoryFileDao.getProduct(4)).thenReturn(testProducts[2]);
        when(mockInventoryFileDao.getProduct(5)).thenReturn(testProducts[3]);
        when(mockInventoryFileDao.getProduct(6)).thenReturn(null);
        when(mockInventoryFileDao.getProduct(7)).thenReturn(testProducts[4]);

        ShoppingCart expectedCart = new ShoppingCart(3);
        expectedCart.getContents().add(testProducts[4]);

        ShoppingCart cart = testShoppingCarts[2];
        cart.getContents().add(new Product(7, "Clefairy", 10, 4.00f));
        cart.calculateTotalPrice();
        shoppingCartFileDao.refreshCart(3, inventoryController);

        // Analyze
        assertEquals(expectedCart.getContents(), cart.getContents());
    }

    @Test
    public void testCheckout() throws IOException{
        // Invoke
        HashSet<Product> emptyContents = new HashSet<Product>();

        when(mockInventoryFileDao.getProduct(2)).thenReturn(testProducts[0]);
        when(mockInventoryFileDao.getProduct(3)).thenReturn(testProducts[1]);
        when(mockInventoryFileDao.getProduct(4)).thenReturn(testProducts[2]);
        when(mockInventoryFileDao.getProduct(5)).thenReturn(testProducts[3]);
        when(mockInventoryFileDao.getProduct(6)).thenReturn(null);

        testShoppingCarts[1].addToCart(testProducts[1]);
        testShoppingCarts[1].addToCart(testProducts[2]);
        testShoppingCarts[2].addToCart(new Product(6,"Clefairy", 3,1.00f));

        ShoppingCart cart = shoppingCartFileDao.checkout(2, inventoryController);
        ShoppingCart nullCart = shoppingCartFileDao.checkout(3, inventoryController);

        assertNull(nullCart);
        assertEquals(emptyContents,cart.getContents());
    }
}
