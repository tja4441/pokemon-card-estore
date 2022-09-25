package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface InventoryDao {
    
    /**
     * Deletes a {@linkplain Product product} when given a product id
     * 
     * @param id the internal {@link Product product} identifier
     * 
     * @return true if {@link Product product} was deleted, false if product doesn't exist
     * 
     * @throws IOException if system encounters any situation that inhibits deletion
     */
    boolean deleteProduct(int id) throws IOException;
}
