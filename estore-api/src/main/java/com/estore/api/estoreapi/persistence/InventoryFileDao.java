package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.Map;

import com.estore.api.estoreapi.model.Product;

public class InventoryFileDao implements InventoryDao {

    Map<Integer, Product> products;

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) throws IOException {
        synchronized(products){
            if(products.containsKey(id))
                return products.get(id);
            else
                return null;
        }   
    }
    
}
