package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.awt.image.BufferedImage;

import org.springframework.web.multipart.MultipartFile;

import com.estore.api.estoreapi.model.CardType;
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

    /**
     * Retrieves an array of all products that contain the substring parameter
     * as a substring
     * @param substring A substring of the products you are trying to find
     * @return Returns a native array of Products that have the substring in
     * their name
     * @throws IOException if it fails to access this information
     */
    Product[] findProducts(String subString) throws IOException;

    /**
     * Retreives an array of all products where their type is the string specified
     * @param type A String that corresponds to one of the types of pokemon
     * @return Returns an Array of Products that are the type specified
     * @throws IOException if it fails to access the information
     */
    Product[] getProductsType(CardType type) throws IOException;

     /**
     * Creates and Stores product image
     * 
     * @param file {@link MultipartFile file} containing image data
     * 
     * @param id int corresponding to the id of the product that this image
     *          will be added to
     * 
     * @return The {@link BufferedImage image} that was created
     * 
     * @throws IOException
     */
    BufferedImage createImage(MultipartFile file) throws IOException;

    /**
     * Retrieves a product image with the given id
     * 
     * @param id The id of the product image to get
     * 
     * @return a {@link String Base64 binary string} of the image
     * 
     * @throws IOException if an issue with storage
     */
    String getImage(String name) throws IOException;
}

