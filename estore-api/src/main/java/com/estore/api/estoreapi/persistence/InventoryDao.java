package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

public interface InventoryDao {

    /**
     * Retrieves a product with the given id
     * @param id The id of the product to get
     * @return a product object with the matching id
     * @throws IOException if an issue with storage
     */
    Product getProduct(int id) throws IOException;
    
}
