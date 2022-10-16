package com.estore.api.estoreapi.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    /**
     * Deletes a {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id The id of the {@link ShoppingCart cart} to delete
     * 
     * @return ResponseEntity HTTP status of OK if deleted, NOT_FOUND if not found, and INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Daniel Pittman
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ShoppingCart> deleteCart(@PathVariable int id) {
        LOG.info("DELETE /ShoppingCarts/" + id);

        try {
            Boolean cartDeleted = shoppingCartDao.deleteCart(id);
            if (cartDeleted != false) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain ShoppingCart cart} with the provided {@link ShoppingCart cart} object, if it exists
     * 
     * @param shoppingCart The {@link ShoppingCart cart} to update
     * 
     * @return ResponseEntity with updated {@link ShoppingCart cart} object and HTTP status of OK if updated,
     *         ResponseEntity with HTTP status of CONFLICT if not found or update not valid,
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Daniel Pittman
     */
    @PutMapping("")
    public ResponseEntity<ShoppingCart> updateCart(@RequestBody ShoppingCart shoppingCart) {
        LOG.info("PUT /ShoppingCarts " + shoppingCart);
        try {
            ShoppingCart updatedCart = shoppingCartDao.updateCart(shoppingCart);
            if (p != null) {
                return new ResponseEntity<ShoppingCart>(updatedCart,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain ShoppingCart cart} given id
     * 
     * @param id The id of the {@link ShoppingCart cart} to get
     * 
     * @return ResponseEntity with updated {@link ShoppingCart cart} object and HTTP status of OK if found,
     *         ResponseEntity with HTTP status of CONFLICT if not found,
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Daniel Pittman
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable int id) {
        LOG.info("GET /ShoppingCarts/" + id);
        try {
            ShoppingCart cart = shoppingCartDao.getCart(id);
            if (cart != null) {
                return new ResponseEntity<ShoppingCart>(cart,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain ShoppingCart cart} with the provided {@link ShoppingCart cart}
     * 
     * @param cart The {@link ShoppingCart cart} to create
     * 
     * @return ResponseEntity with created {@link ShoppingCart cart} object and HTTP status of CREATED if cart created,
     *         ResponseEntity with HTTP status of CONFLICT if {@link ShoppingCart cart} object already exists,
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Daniel Pittman
     */
    @PostMapping("")
    public ResponseEntity<ShoppingCart> createCart(@RequestBody ShoppingCart cart) {
        LOG.info("POST /ShoppingCarts " + cart);
        try {
            ShoppingCart newCart = shoppingCartDao.createCart(cart);
            if (newCart != null) {
                return new ResponseEntity<ShoppingCart>(newCart,HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
