package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Product class
 * 
 * @author Daniel Pittman
 */
@Tag("Model-tier")
public class ProductTest {
    @Test
    public void testConstructor() {
        // Setup
        int expected_id = 1;
        String expected_name = "Charmander";
        int expected_quantity = 2;
        float expected_price = 100.00f;

        // Invoke
        Product product = new Product(expected_id,expected_name,expected_quantity,expected_price);

        // Analyze
        assertEquals(expected_id,product.getId());
        assertEquals(expected_name,product.getName());
        assertEquals(expected_quantity, product.getQuantity());
        assertEquals(expected_price, product.getPrice());
    }

    @Test
    public void testName() {
        // Setup
        int id = 1;
        String name = "Charmander";
        int quantity = 2;
        float price = 100.00f;
        Product product = new Product(id,name,quantity,price);

        String expected_name = "Charmander";

        // Invoke
        product.setName(expected_name);

        // Analyze
        assertEquals(expected_name,product.getName());
    }

    @Test
    public void testQuantity() {
        // Setup
        int id = 1;
        String name = "Charmander";
        int quantity = 2;
        float price = 100.00f;
        Product product = new Product(id,name,quantity,price);

        int expected_quantity = 2;

        // Invoke
        product.setQuantity(expected_quantity);

        // Analyze
        assertEquals(expected_quantity,product.getQuantity());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 1;
        String name = "Charmander";
        int quantity = 2;
        float price = 100.00f;
        Product product = new Product(id,name,quantity,price);

        float expected_price = 100.00f;

        // Invoke
        product.setPrice(expected_price);

        // Analyze
        assertEquals(expected_price,product.getPrice());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 1;
        String name = "Charmander";
        int quantity = 2;
        float price = 100.00f;
        String expected_string = String.format(Product.STRING_FORMAT,id,name,quantity,price);
        Product product = new Product(id,name,quantity,price);

        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
    
}
