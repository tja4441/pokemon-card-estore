package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.Map;

import com.estore.api.estoreapi.model.Product;

public class InventoryFileDao implements InventoryDao {

    Map<String, Product> products;

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product getProduct(String name) throws IOException {
        synchronized(products){
            if(products.containsKey(name))
                return products.get(name);
            else
                return null;
        }   
    }
    
}
