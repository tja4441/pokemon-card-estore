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

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Test the Inventory File DAO class
 * 
 * @author Daniel Pittman
 */
@Tag("Persistence-tier")
public class InventoryFileDaoTest {
    InventoryFileDao inventoryFileDao;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupInventoryFileDao() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[4];
        testProducts[0] = new Product(2,"Pikachu",Type.ELECTRIC, 1,100.00f);
        testProducts[1] = new Product(3, "Bulbasaur",Type.GRASS,2,2.50f);
        testProducts[2] = new Product(4,"Squirtle",Type.WATER,1,1.00f);
        testProducts[3] = new Product(5, "Charmander",Type.FIRE,1,50.05f);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Product array above
        when(mockObjectMapper.readValue(new File("Charmander_Is_Better.txt"),Product[].class)).thenReturn(testProducts);
        when(mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockObjectMapper);
        inventoryFileDao = new InventoryFileDao("Charmander_Is_Better.txt",mockObjectMapper);
    }

    @Test
    public void testGetProducts() {
        // Invoke
        Product[] products = inventoryFileDao.getProducts();

        // Analyze
        assertEquals(products.length,testProducts.length);
        for(int i = 0; i < testProducts.length; ++i){
            assertEquals(products[i],testProducts[i]);
        }

    }

    @Test
    public void testFindProducts() {
        // Invoke
        Product[] products = inventoryFileDao.findProducts("l");
        
        // Analyze
        assertEquals(products.length,2);
        assertEquals(products[0],testProducts[1]);
        assertEquals(products[1],testProducts[2]);
    }

    @Test
    public void testGetProduct() {
        // Invoke
        Product product = inventoryFileDao.getProduct(2);

        // Analyze
        assertEquals(product,testProducts[0]);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDao.deleteProduct(2),
                            "Unexpected exception thrown - Pikachu wouldn't go away :(");
        
        // Analyze
        assertEquals(result,true);
        assertEquals(inventoryFileDao.products.size(),testProducts.length - 1);
    }

    @Test
    public void testCreateProduct() throws IOException {
        // Setup
        Product product = new Product(1,"Mew",Type.PSYCHIC,1,0.50f);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDao.createProduct(product),
                                    "Unexpected exception thrown - Mew was too powerful :(");

         // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDao.getProduct(product.getId());
        assertEquals(actual.getId(),product.getId());
        assertEquals(actual.getName(),product.getName());
        assertEquals(actual.getType(),product.getType());
        assertEquals(actual.getQuantity(),product.getQuantity());
        assertEquals(actual.getPrice(),product.getPrice());
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product product = inventoryFileDao.createProduct(new Product(4,"Wartortle",Type.WATER,1,10.00f));

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDao.updateProduct(product),
                                "Unexpected exception thrown - Squirtle didn't evolve");
        
        // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDao.getProduct(product.getId());
        assertEquals(actual,product);
    }

    @Test
    public void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper)
            .writeValue(any(File.class),any(Product[].class));
        
        Product product = new Product(6, "Raichu",Type.ELECTRIC,1,20.00f);

        assertThrows(IOException.class,
                        () -> inventoryFileDao.createProduct(product),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound() {
        // Invoke
        Product product = inventoryFileDao.getProduct(10);

        // Analyze
        assertEquals(product,null);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> inventoryFileDao.deleteProduct(10),
                             "Unexpected exception thrown - product doesn't exist");

        // Analyze
        assertEquals(result,false);
        assertEquals(inventoryFileDao.products.size(),testProducts.length);
    }

    @Test
    public void testUpdateProductNotFound() throws IOException {
        // Setup
        Product product = new Product(10, "Lucario",Type.FIGHTING,1,20.00f);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDao.updateProduct(product),
                                                "Unexpected exception thrown - lucario was trying to go to id 10");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testCreateProductSameName() throws IOException {
        // Invoke
        Product product = inventoryFileDao.createProduct(new Product(11,"Charmander",Type.FIRE,1,50.05f));

        // Analyze
        assertNull(product);
    }

    @Test
    public void testCreateProductSpaceAsName() throws IOException {
        // Invoke
        Product product = inventoryFileDao.createProduct(new Product(8," ",Type.NORMAL,1,50.05f));

        // Analyze
        assertNull(product);
    }

    @Test
    public void testCreateProductNegativePrice() throws IOException {
        // Invoke
        Product product = inventoryFileDao.createProduct(new Product(8,"Clefairy",Type.FAIRY,1,-50.05f));

        // Analyze
        assertNull(product);
    }

    @Test
    public void testCreateProductNegativeQuantity() throws IOException {
        // Invoke
        Product product = inventoryFileDao.createProduct(new Product(8,"Clefairy",Type.FAIRY,-2,50.05f));

        // Analyze
        assertNull(product);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.enable(SerializationFeature.INDENT_OUTPUT)).thenReturn(mockObjectMapper);

        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("Ash_Ketchum.txt"),Product[].class);
        
        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new InventoryFileDao("Ash_Ketchum.txt",mockObjectMapper),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductsByType() throws IOException {
        // Invoke
        Product[] products = inventoryFileDao.getProductsType("FIRE");

        // Analyze
        assertEquals(products.length, 1);
        assertEquals(products[0], testProducts[3]);
    }
}
