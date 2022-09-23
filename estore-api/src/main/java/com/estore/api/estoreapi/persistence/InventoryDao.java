package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface InventoryDao {

    /**
     * Retrieves a product with the given name
     * @param name The name of the product to get
     * @return a product object with the matching name
     * @throws IOException if an issue with storage
     */
    Product getProduct(String name) throws IOException;
    
}
