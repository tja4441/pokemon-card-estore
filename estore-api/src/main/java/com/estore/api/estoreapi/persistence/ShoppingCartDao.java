package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.controller.InventoryController;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;

/**
 * Defines the interface mediating Shopping Cart persistance
 * 
 * @author Daniel Pittman
 */
public interface ShoppingCartDao {
    
    /**
     * Updates and saves a {@linkplain ShoppingCart cart}
     * 
     * @param {@link Shoppingcart cart} object to be updated and saved
     * 
     * @return updated {@link ShoppingCart cart} if successful, null if otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    ShoppingCart updateCart(ShoppingCart cart) throws IOException;

    /**
     * Retrieves a {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id the id of the {@link ShoppingCart cart} to get
     * 
     * @return a {@link ShoppingCart cart} object with the matching id
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    ShoppingCart getCart(int id) throws IOException;

    /**
     * Creates and stores {@linkplain Shoppingcart cart}
     * 
     * @param {@link ShoppingCart cart} object being created 
     * 
     * @return the {@link ShoppingCart cart} that was created
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    ShoppingCart createCart(ShoppingCart cart) throws IOException;

    /**
     * Deletes an {@linkplain ShoppinggCart cart} given an id
     * 
     * @param id the internal {@link ShoppingCart cart} identifier
     * 
     * @return true if {@link ShoppingCart cart} was deleted, false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    boolean deleteCart(int id) throws IOException;

    /**
     * Adds an {@linkplain Product product} to the {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id The identifier of the {@link ShoppingCart cart} object being added to
     * 
     * @param product the {@link Product product} to be added to the {@link ShoppingCart cart} object
     * 
     * @return the {@link ShoppingCart cart} object being added to
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    ShoppingCart addToCart(int id, Product product) throws IOException;

    /**
     * Deletes an {@linkplain Product product} from the {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id the identifier of the {@link ShoppingCart cart} object being added to
     * 
     * @param product the {@link Product product} to be deleted from the {@link ShoppingCart cart} object
     * 
     * @return the {@link ShoppingCart cart} object being removed from
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    ShoppingCart deleteFromCart(int id, Product product) throws IOException;

    /**
     * Refreshes the {@linkplain Product products} inside of the {@linkplain ShoppingCart cart} with the given id
     * 
     * @param id the identifier of the {@link ShoppingCart cart} object being refreshed
     * 
     * @param inventoryController the controller of the {@linkplain Product products} within the {@link ShoppingCart cart}
     * 
     * @return true if {@link Product products} are not removed from cart due to no remaining inventory, false otherwise
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    boolean refreshCart(int id, InventoryController inventoryController) throws IOException;

    /**
     * Checkouts the contents of the {@linkplain ShoppingCart cart} object with the given id
     * 
     * @param id the identifier of the {@link ShoppingCart cart} object being refreshed
     * 
     * @param inventoryController the controller of the {@linkplain Product products} within the {@link ShoppingCart cart}
     * 
     * @return the {@link ShoppingCart cart} object being checked out
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    ShoppingCart checkout(int id, InventoryController inventoryController) throws IOException;

    /**
     * Reverses the checkout procedure for the {@linkplain ShoppingCart cart} object with the given id
     * 
     * @param id the identifier of the {@link ShoppingCart cart} object having the checkout reversed
     * 
     * @param inventoryController the controller of the {@linkplain Product products} within the {@link ShoppingCart cart}
     * 
     * @return the {@link ShoppingCart cart} object that had the checkout procedure reversed
     * 
     * @throws IOException if underlying storage cannot be accessed
     * 
     * @author Daniel Pittman
     */
    ShoppingCart reverseCheckout(int id, InventoryController inventoryController) throws IOException;
}
