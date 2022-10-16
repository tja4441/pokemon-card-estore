package com.estore.api.estoreapi.persistence;

import java.io.IOException;

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
     * Deletes an {@linkplain ShoppingCart cart} given an id
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

}
