package com.estore.api.estoreapi.controller;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.persistence.ShoppingCartDao;

@RestController
@RequestMapping("ShoppingCarts")
public class ShoppingCartController extends Controller{
    private static final Logger LOG = Logger.getLogger(ShoppingCartController.class.getName());
    private ShoppingCartDao shoppingCartDao;
    private InventoryController inventoryController;

    /**
     * Creates a REST API controller to respond to requests regarding shopping carts
     * 
     * @param shoppingCartDao The shoppingCart data access object to perform CRUD operations
     * @param inventoryController Controller of the inventory for modifying product quantities
     */
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, InventoryController inventoryController) {
        this.shoppingCartDao = shoppingCartDao;
        this.inventoryController = inventoryController;
    }

    public ResponseEntity<ShoppingCart> deleteCart(@PathVariable int id) {
        
    }

    public ResponseEntity<ShoppingCart> updateCart(@RequestBody ShoppingCart shoppingCart) {

    }

    public ResponseEntity<ShoppingCart> getCart(@PathVariable int id) {

    }

    public ResponseEntity<ShoppingCart> createProduct(@RequestBody ShoppingCart cart) {

    }

}
