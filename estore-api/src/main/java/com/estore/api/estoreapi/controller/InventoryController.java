package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDao;

@RestController
@RequestMapping("product")
public class InventoryController extends Controller {
    
    private InventoryDao inventoryDao;

    /**
     * Creates a REST API controller to respond to requests
     * @param inventoryDao The inventory data access object to perfom CRUD operations
     */
    public InventoryController(InventoryDao inventoryDao){
        this.inventoryDao = inventoryDao;
    }
    
    /**
     * Responds to the GET request for a product given name
     * @param name the name used to locate the product
     * @return ResponseEntity product object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    public ResponseEntity<Product> getProduct(@PathVariable String name){
        try {
            Product product = inventoryDao.getProduct(name);
            if(product != null){
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
