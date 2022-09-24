package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface InventoryDao {
    
    /**
     * Retrieves all products in the inventory
     * @return An array of product objects, may be empty
     * @throws IOException if an issue with storage
     */
    Product[] getProducts() throws IOException;

}
