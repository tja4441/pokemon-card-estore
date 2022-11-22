package com.estore.api.estoreapi.controller;

import java.io.IOException;
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

import com.estore.api.estoreapi.model.OrderHistory;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.persistence.ShoppingCartDao;

@RestController
@RequestMapping("ShoppingCarts")
public class ShoppingCartController {
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
            if (cartDeleted) {
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
            if (updatedCart != null) {
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
            ShoppingCart cart = shoppingCartDao.getCart(id, inventoryController);
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
    public ResponseEntity<ShoppingCart> createCart(@RequestBody int id) {
        LOG.info("POST /ShoppingCarts " + id);
        try {
            ShoppingCart newCart = shoppingCartDao.createCart(id);
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

    /**
     * Adds an {@linkplain Product product} to the {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id The identifier of the {@link ShoppingCart cart} object being added to
     * 
     * @param product the {@link Product product} to be added to the {@link ShoppingCart cart} object
     * 
     * @return ResponseEntity with updated {@link ShoppingCart cart} object and HTTP status of ok if cart modified successfully,
     *         ResponseEntity with HTTP status of NOT_FOUND if {@link ShoppingCart cart} object cannot be found,
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Daniel Pittman
     */
    @PutMapping("/{id}/add")
    public ResponseEntity<ShoppingCart> addToCart(@PathVariable int id, @RequestBody Product product) {
        LOG.info("PUT /ShoppingCarts/" + id + "/add/" + product);
        try {
            ShoppingCart updatedCart = shoppingCartDao.addToCart(id,product);
            if (updatedCart != null) {
                return new ResponseEntity<ShoppingCart>(updatedCart,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an {@linkplain Product product} from the {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id the identifier of the {@link ShoppingCart cart} object being added to
     * 
     * @param product the {@link Product product} to be deleted from the {@link ShoppingCart cart} object
     * 
     * @return ResponseEntity with updated {@link ShoppingCart cart} object and HTTP status of ok if cart modified successfully,
     *         ResponseEntity with HTTP status of NOT_FOUND if {@link ShoppingCart cart} object cannot be found,
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Daniel Pittman
     */
    @PutMapping("/{id}/delete")
    public ResponseEntity<ShoppingCart> deleteFromCart(@PathVariable int id, @RequestBody Product product) {
        LOG.info("PUT /ShoppingCarts/" + id + "/delete/" + product);
        try {
            ShoppingCart updatedCart = shoppingCartDao.deleteFromCart(id,product);
            if (updatedCart != null) {
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
     * Refreshes the {@linkplain Product products} inside of the {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id the identifier of the {@link ShoppingCart cart} object being refreshed
     * 
     * @param inventoryController the controller of the {@linkplain Product products} within the {@link ShoppingCart cart}
     * 
     * @return ResponseEntity with boolean indicating if {@link Product product} objects were deleted from {@link ShoppingCart cart} and HTTP status of ok,
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * 
     * @author Daniel Pittman
     */
    @PutMapping("/{id}/refresh")
    public ResponseEntity<Boolean> refreshCart(@PathVariable int id) {
        LOG.info("PUT /ShoppingCarts/" + id + "/refresh" );
        try {
            Boolean nothingDeleted = shoppingCartDao.refreshCart(id,inventoryController);
            return new ResponseEntity<Boolean>(nothingDeleted,HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Checkouts the contents of the {@linkplain ShoppingCart cart} object with the given id
     * 
     * @param id the identifier of the {@link ShoppingCart cart} object being refreshed
     * 
     * @param inventoryController the controller of the {@linkplain Product products} within the {@link ShoppingCart cart}
     * 
     * @return ResponseEntity with checked out {@link ShoppingCart cart} object and HTTP status of ok if cart checked out successfully,
     *         ResponseEntity with HTTP status of BAD_REQUEST if {@link ShoppingCart cart} cannot check out due to inventory shortage,
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{id}/checkout")
    public ResponseEntity<ShoppingCart> checkout(@PathVariable int id) {
        LOG.info("PUT /ShoppingCarts/" + id + "/checkout");
        try {
            ShoppingCart updatedCart = shoppingCartDao.checkout(id,inventoryController);
            if (updatedCart != null) {
                return new ResponseEntity<ShoppingCart>(updatedCart,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to a GET Request for all of the {@linkplain OrderHistory} in the system
     * 
     * @return ResponseEntity with {@link OrderHistory} array and HTTP status of OK if orders are found
     * @return ResponseEntity with HTTP status of NOT_FOUND if no orders were found
     * @return ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/history")
    public ResponseEntity<OrderHistory[]> getAllOrderHistory() {
        LOG.info("GET /history");
        try {
            OrderHistory[] orders = shoppingCartDao.getOrders();
            if(orders != null) {
                return new ResponseEntity<OrderHistory[]>(orders, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to a GET Request for {@linkplain OrderHistory} from a specified user
     * 
     * @param id the identifier for the user
     * 
     * @return ResponseEntity with {@link OrderHistory} array and HTTP status of OK if orders are found
     * @return ResponseEntity with HTTP status of NOT_FOUND if no orders were found
     * @return ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/history/{id}")
    public ResponseEntity<OrderHistory[]> getOrderHistoryByUser(@PathVariable int id) {
        LOG.info("GET /history/" + id);
        try {
            OrderHistory[] orders = shoppingCartDao.searchOrders(id);
            if(orders != null){
                return new ResponseEntity<OrderHistory[]>(orders,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
