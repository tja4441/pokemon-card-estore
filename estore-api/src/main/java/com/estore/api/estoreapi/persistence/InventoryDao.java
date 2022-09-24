package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface for Hero object persistence
 * 
 * @author Timothy Avila
 */
public interface InventoryDao {
    /**
     * Updates and saves a {@linkplain Product product}
     * 
     * @param {@link Product product} object to be updated and saved
     * 
     * @return updated {@link Product product} if successful, null if
     * {@link Product product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;
}

