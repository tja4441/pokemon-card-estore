package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.OrderHistory;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.CardType;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.ShoppingCartDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
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

    private static User createTestUser() {
        User user = new User(1, "Jeff", "");
        return user;
    }

    @Test
    public void testDeleteCart() throws IOException{
        when(mockShoppingCartDao.deleteCart(createTestUser().getId())).thenReturn(true);

        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteCart(createTestUser().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testDeleteCartNotFound() throws IOException{
        when(mockShoppingCartDao.deleteCart(1)).thenReturn(false);

        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteCart(5);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCartHandleException() throws IOException{
        doThrow(new IOException()).when(mockShoppingCartDao).deleteCart(1);

        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteCart(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateCart() throws IOException{
        ShoppingCart cart = new ShoppingCart(3);
        
        when(mockShoppingCartDao.updateCart(cart)).thenReturn(cart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.updateCart(cart);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testUpdateCartConflict() throws IOException{
        ShoppingCart cart = new ShoppingCart(3);
        
        when(mockShoppingCartDao.updateCart(cart)).thenReturn(null);

        ResponseEntity<ShoppingCart> response = shoppingCartController.updateCart(cart);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testUpdateCartHandleException() throws IOException{
        ShoppingCart cart = new ShoppingCart(3);
        
        doThrow(new IOException()).when(mockShoppingCartDao).updateCart(cart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.updateCart(cart);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    

    @Test
    public void testGetCart() throws IOException{
        ShoppingCart cart = new ShoppingCart(1);
        
        when(mockShoppingCartDao.getCart(1,mockInventoryController)).thenReturn(cart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.getCart(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testGetCartNotFound() throws IOException{
        when(mockShoppingCartDao.getCart(1,mockInventoryController)).thenReturn(null);

        ResponseEntity<ShoppingCart> response = shoppingCartController.getCart(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCartHandleException() throws IOException{
        doThrow(new IOException()).when(mockShoppingCartDao).getCart(3,mockInventoryController);

        ResponseEntity<ShoppingCart> response = shoppingCartController.getCart(3);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateCart() throws IOException{
        ShoppingCart cart = new ShoppingCart(1);
        
        when(mockShoppingCartDao.createCart(1)).thenReturn(cart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.createCart(1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testCreateCartConflict() throws IOException{
        when(mockShoppingCartDao.createCart(1)).thenReturn(null);

        ResponseEntity<ShoppingCart> response = shoppingCartController.createCart(1);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateCartHandleException() throws IOException{
        doThrow(new IOException()).when(mockShoppingCartDao).createCart(1);

        ResponseEntity<ShoppingCart> response = shoppingCartController.createCart(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    
    }

    @Test
    public void testAddToCart() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        ShoppingCart cart = new ShoppingCart(1);
        Product nProduct = new Product(5,"Apples",typeArray,10,1.00f);
        Product expectedProduct = new Product(5,"Apple",typeArray,1,1.00f);
        cart.getContents().add(expectedProduct);
        
        when(mockShoppingCartDao.addToCart(1,nProduct)).thenReturn(cart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.addToCart(1, nProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testAddToCartHandleException() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product nProduct = new Product(5,"Apple",typeArray,1,1.00f);
        
        doThrow(new IOException()).when(mockShoppingCartDao).addToCart(1, nProduct);

        ResponseEntity<ShoppingCart> response = shoppingCartController.addToCart(1, nProduct);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddToCartHandleNotFound() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product nProduct = new Product(5,"Apple",typeArray,1,1.00f);
        
        when(mockShoppingCartDao.addToCart(1, nProduct)).thenReturn(null);

        ResponseEntity<ShoppingCart> response = shoppingCartController.addToCart(1, nProduct);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteFromCart() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        ShoppingCart cart = new ShoppingCart(1);
        Product nProduct = new Product(5,"Apple",typeArray,1,1.00f);
        
        when(mockShoppingCartDao.deleteFromCart(1,nProduct)).thenReturn(cart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteFromCart(1, nProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testDeleteFromCartHandleException() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product nProduct = new Product(5,"Apple",typeArray,1,1.00f);
        
        doThrow(new IOException()).when(mockShoppingCartDao).deleteFromCart(1, nProduct);

        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteFromCart(1, nProduct);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteFromCartConflict() throws IOException{
        CardType[] typeArray = new CardType[1];
        typeArray[0] = CardType.GRASS;
        Product nProduct = new Product(5,"Apple",typeArray,1,1.00f);
        
        when(mockShoppingCartDao.deleteFromCart(1, nProduct)).thenReturn(null);

        ResponseEntity<ShoppingCart> response = shoppingCartController.deleteFromCart(1, nProduct);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test    
    public void testRefresh() throws IOException{
        boolean daoRefreshesTrue = true;
        when(mockShoppingCartDao.refreshCart(1, mockInventoryController)).thenReturn(daoRefreshesTrue);

        ResponseEntity<Boolean> response1 = shoppingCartController.refreshCart(1);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(daoRefreshesTrue, response1.getBody());

        boolean daoRefreshesFalse = false;
        when(mockShoppingCartDao.refreshCart(1, mockInventoryController)).thenReturn(daoRefreshesFalse);

        ResponseEntity<Boolean> response2 = shoppingCartController.refreshCart(1);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(daoRefreshesFalse, response2.getBody());
    }

    @Test
    public void testRefreshHandleException() throws IOException {
        doThrow(new IOException()).when(mockShoppingCartDao).refreshCart(1, mockInventoryController);
        ResponseEntity<Boolean> response = shoppingCartController.refreshCart(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCheckout() throws IOException {
        ShoppingCart testCart = new ShoppingCart(1);
        when(mockShoppingCartDao.checkout(1, mockInventoryController)).thenReturn(testCart);

        ResponseEntity<ShoppingCart> response = shoppingCartController.checkout(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testCart, response.getBody());
    }

    @Test
    public void testCheckoutBadRequest() throws IOException {
        when(mockShoppingCartDao.checkout(1, mockInventoryController)).thenReturn(null);
        ResponseEntity<ShoppingCart> response = shoppingCartController.checkout(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testCheckoutHandlesException() throws IOException {
        doThrow(new IOException()).when(mockShoppingCartDao).checkout(1, mockInventoryController);
        ResponseEntity<ShoppingCart> response = shoppingCartController.checkout(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetAllOrdersSucceeds() throws IOException {
        OrderHistory history = new OrderHistory(0, null, 0, null);
        OrderHistory[] histories = new OrderHistory[1];
        histories[0] = history;
        when(mockShoppingCartDao.getOrders()).thenReturn(histories);

        ResponseEntity<OrderHistory[]> response = shoppingCartController.getAllOrderHistory();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllOrdersFindsNothing() throws IOException {
        when(mockShoppingCartDao.getOrders()).thenReturn(null);

        ResponseEntity<OrderHistory[]> response = shoppingCartController.getAllOrderHistory();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllOrdersHandlesError() throws IOException {
        doThrow(new IOException()).when(mockShoppingCartDao).getOrders();
        ResponseEntity<OrderHistory[]> response = shoppingCartController.getAllOrderHistory();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetOrdersByIDSucceeds() throws IOException {
        OrderHistory history = new OrderHistory(0, null, 0, null);
        OrderHistory[] histories = new OrderHistory[1];
        histories[0] = history;
        when(mockShoppingCartDao.searchOrders(1)).thenReturn(histories);

        ResponseEntity<OrderHistory[]> response = shoppingCartController.getOrderHistoryByUser(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetOrdersByIDFindsNothing() throws IOException {
        when(mockShoppingCartDao.searchOrders(1)).thenReturn(null);

        ResponseEntity<OrderHistory[]> response = shoppingCartController.getOrderHistoryByUser(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetOrdersByIDHandlesErrors() throws IOException {
        doThrow(new IOException()).when(mockShoppingCartDao).searchOrders(1);
        ResponseEntity<OrderHistory[]> response = shoppingCartController.getOrderHistoryByUser(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
