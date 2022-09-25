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
     * Retreives a Product From storage with the given ID
     * 
     * @param id the id of the Product to get
     * 
     * @return A Product with the matching ID, null if no Product found
     * 
     * @throws IOException
     */
    Product getProduct(int id) throws IOException;

    /**
     * Saves the current State of the map to a file
     * 
     * @return If the objects were properly saved
     * 
     * @throws IOException
     */
    Boolean save() throws IOException;

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
