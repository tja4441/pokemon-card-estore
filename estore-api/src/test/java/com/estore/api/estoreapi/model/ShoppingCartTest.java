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
    ShoppingCart testCart;

    @BeforeEach
    public void setupTestCart(){
        int id = 1;
        testCart = new ShoppingCart(id);
    }

    @Test
    public void testConstructor() {
        int expectedId = 2;
        float expectedTotalPrice = 0.00f;
        HashSet<Product> expectedContents = new HashSet<Product>();

        ShoppingCart cart = new ShoppingCart(expectedId);

        assertEquals(expectedId, cart.getId());
        assertEquals(expectedContents, cart.getContents());
        assertEquals(expectedTotalPrice, cart.GetTotalPrice());
    }

    @Test
    public void testAddProduct() {
        HashSet<Product> expectedContents = new HashSet<Product>();
        Product p = new Product(1, "Carrots", 50, 2.10f);
        expectedContents.add(p);
        testCart.addToCart(p);
        float expectedTotalPrice = p.getPrice();

        assertEquals(expectedContents, testCart.getContents());
        assertEquals(expectedTotalPrice, testCart.GetTotalPrice());
    }

    @Test
    public void testRemoveProduct() {
        HashSet<Product> expectedContents = new HashSet<Product>();
        Product p = new Product(1, "Carrots", 50, 2.10f);
        expectedContents.add(p);
        testCart.addToCart(p);

        expectedContents.remove(p);
        testCart.removeFromCart(p);

        assertEquals(expectedContents, testCart.getContents());
        assertEquals(0.00f, testCart.GetTotalPrice());
    }

    @Test
    public void testUpdateProductInCart() {
        HashSet<Product> expectedContents = new HashSet<Product>();
        Product oldP = new Product(1, "Carrots", 50, 2.10f);
        Product newP = new Product(1, "Carrot", 25, 2f);
        expectedContents.add(newP);
        float expectedTotalPrice = newP.getPrice();
        
        testCart.addToCart(oldP);
        testCart.updateProductInCart(oldP, newP);

        assertEquals(expectedContents, testCart.getContents());
        assertEquals(expectedTotalPrice, testCart.GetTotalPrice());
    }

    @Test
    public void testSizeOf() {
        HashSet<Product> expectedContents = new HashSet<Product>();
        Product p = new Product(1, "Carrots", 50, 2.10f);
        expectedContents.add(p);
        int expectedSize = expectedContents.size();
        
        testCart.addToCart(p);

        assertEquals(expectedSize, testCart.size());
    }

    @Test
    public void testToString() {
        int id = 1;
        HashSet<Product> contents = new HashSet<Product>();
        float totalPrice = 0.00f;
        String expectedString = String.format(ShoppingCart.STRING_FORMAT, id, contents, totalPrice);

        String actualString = testCart.toString();

        assertEquals(expectedString, actualString);
    }

    @Test
    public void testEqualCartToCart() {
        int id = 1;
        ShoppingCart cart = new ShoppingCart(id);

        boolean equal = cart.equals(testCart);

        assertEquals(true, equal);
    }

    @Test
    public void testEqualCartToNonCart() {
        Product p = new Product(1, "Carrots", 50, 2.10f);

        boolean equal = testCart.equals(p);

        assertEquals(false, equal);
    }



}
