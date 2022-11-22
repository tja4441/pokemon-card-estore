package com.estore.api.estoreapi.controller;
/**
 * Handles the REST API requests for the Product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Team E
 */
 
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import com.estore.api.estoreapi.model.CardType;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDao;

@RestController
@RequestMapping("products")
public class InventoryController {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDao inventoryDao;

    /**
     * Creates a REST API controller to respond to requests
     * @param inventoryDao The inventory data access object to perfom CRUD operations
     */
    public InventoryController(InventoryDao inventoryDao){
        this.inventoryDao = inventoryDao;
    }

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to delete
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.info("DELETE /products/" + id);

        try {
            Boolean productDeleted = inventoryDao.deleteProduct(id);
            if (productDeleted != false)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     /**
     * Updates the {@linkplain Product product} with the provided {@linkplain Product product} object, if it exists
     * 
     * @param product The {@link Product product} to update
     * 
     * @return ResponseEntity with updated {@link Product product} object and HTTP status of OK if updated
     * ResponseEntity with HTTP status of CONFLICT if not found or update to product not valid
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);

        try {
            Product p = inventoryDao.updateProduct(product);
            if (p != null){
            return new ResponseEntity<Product>(p,HttpStatus.OK);
            }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all products
     * @return ResponseEntity with array of product objects (may be emty) and HTTP 
     * status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts(){
        LOG.info("GET /products");
        try {
            Product[] products = inventoryDao.getProducts();
            if(products != null){
                return new ResponseEntity<Product[]>(products,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } 
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a product given id
     * @param id the id used to locate the product
     * @return ResponseEntity product object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        LOG.info("GET /products/" + id);
        try {
            Product product = inventoryDao.getProduct(id);
            if(product != null){
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
    /**
     * Creates a {@linkplain Product product} with the provided product
     * 
     * @param product - The {@link Product product} to create
     * 
     * @return ResponseEntity with created {@link Product product} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Product product} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);

        try {
            Product newProduct = inventoryDao.createProduct(product);
            if (newProduct != null) {
                return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to a get request for an array of products that contain a
     * specified substring in their name property
     * @param name the substring that is being searched for in products
     * @return ResponseEntity with a status of OK with product
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Product[]> searchProducts(@PathVariable String name) {
        LOG.info("GET /products/name/"+name);
        try {
            Product[] products = inventoryDao.findProducts(name);
            if (products.length == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Product[]>(products,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to a get request for an array of products that are a certain type
     * @param type the string that represents the type being searched for
     * @return ResponseEntity with a status of OK with the products
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<Product[]> getProductsByType(@PathVariable String type) {
        LOG.info("GET /products/type/"+type);
        try {
            Product[] products = inventoryDao.getProductsType(CardType.valueOf(type));
            if (products.length == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Product[]>(products,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/image")
    public ResponseEntity<BufferedImage> uploadImage(@RequestParam("file") MultipartFile file) {
        LOG.info("POST /products/image " );
        try {
            inventoryDao.createImage(file);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch(IOException io) {
            LOG.log(Level.SEVERE, io.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}/image")
    public ResponseEntity<String> getImage(@PathVariable String name) {
        try {
            String imageSrc = inventoryDao.getImage(name);
            if( imageSrc != null ) {
                return new ResponseEntity<String>(imageSrc, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch( IOException io ) {
            LOG.log(Level.SEVERE, io.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}