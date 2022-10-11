package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        testProducts[0] = new Product(2,"Pikachu",1,100.00f);
        testProducts[1] = new Product(3, "Bulbasaur",2,2.50f);
        testProducts[2] = new Product(4,"Squirtle",1,1.00f);
        testProducts[3] = new Product(5, "Charmander",1,50.05f);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Product array above
        when(mockObjectMapper
            .readValue(new File("Charmander-Is-Better.txt"),Product[].class))
                .thenReturn(testProducts);
        inventoryFileDao = new InventoryFileDao("Pikachu-Supremacy.txt",mockObjectMapper);
    }

    @Test
    public void testGetProducts() throws IOException {
        // Invoke
        Product[] products = inventoryFileDao.getProducts();

        // Analyze
        assertEquals(products.length,testProducts.length);
        for(int i = 0; i < testProducts.length; ++i){
            assertEquals(products[i],testProducts[i]);
        }

    }

    @Test
    public void testFindProducts() throws IOException {
        // Invoke
        Product[] products = inventoryFileDao.findProducts("l");
        
        // Analyze
        assertEquals(products.length,2);
        assertEquals(products[0],testProducts[1]);
        assertEquals(products[1],testProducts[2]);
    }

    @Test
    public void testGetProduct() throws IOException {
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
        Product product = new Product(1,"Mew",1,0.50f);

        // Invoke
        Product result = assertDoesNotThrow(() -> inventoryFileDao.createProduct(product),
                                    "Unexpected exception thrown - Mew was too powerful :(");

         // Analyze
        assertNotNull(result);
        Product actual = inventoryFileDao.getProduct(product.getId());
        assertEquals(actual.getId(),product.getId());
        assertEquals(actual.getName(),product.getName());
        assertEquals(actual.getQuantity(),product.getQuantity());
        assertEquals(actual.getPrice(),product.getPrice());
    }

    @Test
    public void testUpdateProduct() {
        // Setup
        //Product product = 
    }

    @Test
    public void testSaveException() {

    }

    @Test
    public void testGetProductNotFound() {

    }

    @Test
    public void testDeleteProductNotFound() {

    }

    @Test
    public void testUpdateProductNotFound() {

    }

    @Test
    public void testCreateProductSameName(){

    }

    @Test
    public void testCreateProductSpaceAsName(){

    }

    @Test
    public void testCreateProductNegativePrice(){

    }

    @Test
    public void testCreateProductNegativeID(){

    }

    @Test
    public void testConstructorException() throws IOException {

    }
    
}
