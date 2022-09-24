package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.InventoryDao;
import com.estore.api.estoreapi.model.Product;

public class InventoryController extends Controller {
    private static final Logger LOG = Logger.getLogger(InventoryController.class.getName());
    private InventoryDao inventoryDao;

    /**
     * Updates the {@linkplain Product product} with the provided {@linkplain Product product} object, if it exists
     * 
     * @param hero The {@link Product product} to update
     * 
     * @return ResponseEntity with updated {@link Product product} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);

        try {
            Product p = inventoryDao.updateProduct(product);
            if (p != null)
            return new ResponseEntity<Product>(p,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch(IOException e) {
        LOG.log(Level.SEVERE,e.getLocalizedMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
}