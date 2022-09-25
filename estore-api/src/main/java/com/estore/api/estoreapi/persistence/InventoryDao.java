package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

/**
 * Defines the interface mediating Product persistance
 * 
 * @author Team Engenuity
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

    /**
     * Retrieves a product with the given id
     * @param id The id of the product to get
     * @return a product object with the matching id
     * @throws IOException if an issue with storage
     */
    Product getProduct(int id) throws IOException;
  
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

    /**
     * Retrieves all products in the inventory
     * @return An array of product objects, may be empty
     * @throws IOException if an issue with storage
     */
    Product[] getProducts() throws IOException;
}

