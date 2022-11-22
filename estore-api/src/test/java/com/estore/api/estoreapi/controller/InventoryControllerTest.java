package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.CardType;
import com.estore.api.estoreapi.persistence.InventoryDao;

/**
 * Test the Inventory Controller Class
 */
@Tag("Controller-tier")
public class InventoryControllerTest {
    private InventoryController inventoryController;
    private InventoryDao mockInventoryDao;

    @BeforeEach
    public void setupInventoryController(){
        mockInventoryDao = mock(InventoryDao.class);
        inventoryController = new InventoryController(mockInventoryDao);
    }

    @Test
    public void testGetProduct() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;

        Product product = new Product(5,"Apple",typeArray,1,1.00f);

        when(mockInventoryDao.getProduct(product.getId())).thenReturn(product);


        ResponseEntity<Product> response = inventoryController.getProduct(product.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());

    }

    @Test
    public void testGetProductNotFound() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;

        Product product = new Product(5,"Apple",typeArray,1,1.00f);

        when(mockInventoryDao.getProduct(product.getId())).thenReturn(null);


        ResponseEntity<Product> response = inventoryController.getProduct(product.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws IOException{
        int productId = 2;

        doThrow(new IOException()).when(mockInventoryDao).getProduct(productId);

        ResponseEntity<Product> response = inventoryController.getProduct(productId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testCreateProduct() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product product = new Product(5,"Apple",typeArray,1,1.00f);

        when(mockInventoryDao.createProduct(product)).thenReturn(product);

        ResponseEntity<Product> response = inventoryController.createProduct(product);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product, response.getBody());

    }

    @Test
    public void testCreateProductFailed() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product product = new Product(5,"Apple",typeArray,1,1.00f);

        when(mockInventoryDao.createProduct(product)).thenReturn(null);

        ResponseEntity<Product> response = inventoryController.createProduct(product);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateProductHandleException() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product product = new Product(5,"Apple",typeArray,1,1.00f);

        doThrow(new IOException()).when(mockInventoryDao).createProduct(product);

        ResponseEntity<Product> response = inventoryController.createProduct(product);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product product = new Product(5,"Apple",typeArray,1,1.00f);

        when(mockInventoryDao.updateProduct(product)).thenReturn(product);

        ResponseEntity<Product> response = inventoryController.updateProduct(product);

        response = inventoryController.updateProduct(product);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product product = new Product(5,"Apple",typeArray,1,1.00f);

        doThrow(new IOException()).when(mockInventoryDao).updateProduct(product);

        ResponseEntity<Product> response = inventoryController.updateProduct(product);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product[] products = new Product[2];
        products[0] = new Product(5,"Apple",typeArray,1,1.00f);
        products[1] = new Product(2,"Pear",typeArray,100,0.50F);

        when(mockInventoryDao.getProducts()).thenReturn(products);

        ResponseEntity<Product[]> response = inventoryController.getProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetProductsHandleException() throws IOException{
        
        doThrow(new IOException()).when(mockInventoryDao).getProducts();

        ResponseEntity<Product[]> response = inventoryController.getProducts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchProducts() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        String searchString = "berries";
        Product[] products = new Product[3];
        products[0] = new Product(1,"Blueberries",typeArray,10,1.00F);
        products[1] = new Product(2,"Banana",typeArray,100,0.50F);
        products[2] = new Product(3,"Strawberries",typeArray,40,1.50F);

        when(mockInventoryDao.findProducts(searchString)).thenReturn(products);

        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());

    }

    @Test
    public void testSearchProductsHandleException() throws IOException{
        String searchString = "geh";

        doThrow(new IOException()).when(mockInventoryDao).findProducts(searchString);

        ResponseEntity<Product[]> response = inventoryController.searchProducts(searchString);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException{
        int productId = 2;

        when(mockInventoryDao.deleteProduct(productId)).thenReturn(true);

        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    } 

    @Test
    public void testDeleteProductNotFound() throws IOException{
        int productId = 2;

        when(mockInventoryDao.deleteProduct(productId)).thenReturn(false);

        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException{
        int productId = 2;

        doThrow(new IOException()).when(mockInventoryDao).deleteProduct(productId);

        ResponseEntity<Product> response = inventoryController.deleteProduct(productId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    // @Test
    // public void testGetProductsByType() throws IOException {
    //     CardType[] typeArray = new CardType[1];
    //     typeArray[0] = CardType.GRASS;
    //     String searchType = "GRASS";
    //     Product[] products = new Product[3];
    //     products[0] = new Product(1,"Blueberries",typeArray,10,1.00F);
    //     products[1] = new Product(2,"Banana",typeArray,100,0.50F);
    //     products[2] = new Product(3,"Strawberries",typeArray,40,1.50F);

    //     when(mockInventoryDao.getProductsType(CardType.GRASS)).thenReturn(products);

    //     ResponseEntity<Product[]> response = inventoryController.searchProducts(searchType);

    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(products, response.getBody());
    // }

    @Test
    public void testUploadImage() throws IOException{
        MultipartFile file = null;
        when(mockInventoryDao.createImage(file)).thenReturn(null);
        ResponseEntity<BufferedImage> response = inventoryController.uploadImage(file);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    
    @Test
    public void testUploadImageHandlesExeption() throws IOException{
        MultipartFile file = null;
        doThrow(new IOException()).when(mockInventoryDao).createImage(file);
        ResponseEntity<BufferedImage> response = inventoryController.uploadImage(file);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
