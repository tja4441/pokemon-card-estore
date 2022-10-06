package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;

@Tag("Model-Tier")
public class ShoppingCartTest {
    @Test
    public void testCtorParams() {
        HashSet<Product> expectedSet = new HashSet<Product>();
        expectedSet.add(new Product(1, "Carrots", 50, 2.30f));

        ShoppingCart cart = new ShoppingCart(expectedSet);

        assertEquals(expectedSet, cart.getCartSet());
    }

    @Test
    public void testCtorNoParams() {
        HashSet<Product> expectedSet = new HashSet<Product>();

        ShoppingCart cart = new ShoppingCart();

        assertEquals(expectedSet, cart.getCartSet());
    }

    @Test
    public void testAddProduct() {
        HashSet<Product> expectedSet = new HashSet<Product>();
        
        ShoppingCart cart = new ShoppingCart(expectedSet);

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
        
        ShoppingCart cart = new ShoppingCart(expectedSet);

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
        
        ShoppingCart cart = new ShoppingCart();
        cart.addToCart(oldP);
        cart.updateProductInCart(oldP, newP);

        assertEquals(expectedSet, cart.getCartSet());
    }

    @Test
    public void testSizeOf() {
        HashSet<Product> expectedSet = new HashSet<Product>();
        Product p = new Product(1, "Carrots", 50, 2.10f);
        expectedSet.add(p);
        
        ShoppingCart cart = new ShoppingCart(expectedSet);

        assertEquals(cart.size(), 1);
    }
}
