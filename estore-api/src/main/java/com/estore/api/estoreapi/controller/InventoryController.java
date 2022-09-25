package com.estore.api.estoreapi.controller;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDao;

@RestController
@RequestMapping("product")
public class InventoryController extends Controller {
    
    private InventoryDao inventoryDao;
    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    /**
     * Creates a REST API controller to respond to requests
     * @param inventoryDao The inventory data access object to perfom CRUD operations
     */
    public InventoryController(InventoryDao inventoryDao){
        this.inventoryDao = inventoryDao;
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
        try {
            Product product = inventoryDao.getProduct(id);
            if(product != null){
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
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
}
