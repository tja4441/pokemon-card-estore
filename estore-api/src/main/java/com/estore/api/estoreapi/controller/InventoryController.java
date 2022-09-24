package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.InventoryDao;
@RestController
@RequestMapping("products")
public class InventoryController extends Controller {
    
    private InventoryDao inventoryDao;

    @GetMapping("")
    public ResponseEntity<Product[]> getProducts(){
        try {
            Product[] products = inventoryDao.getProducts();
            return new ResponseEntity<Product[]>(products,HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
