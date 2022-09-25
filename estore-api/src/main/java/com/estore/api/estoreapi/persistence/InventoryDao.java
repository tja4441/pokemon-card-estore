package com.estore.api.estoreapi.persistence;

import com.estore.api.estoreapi.model.Product;
import java.io.IOException;

/**
 * Defines the interface mediating Product persistance
 * 
 * @author Team Engenuity
 */
public interface InventoryDao {
    /**
     * Creates and Stores product
     * 
     * @param product
     * 
     * @return The Product that was created
     * 
     * @throws IOException
     */
    Product createProduct(Product product) throws IOException;


}
