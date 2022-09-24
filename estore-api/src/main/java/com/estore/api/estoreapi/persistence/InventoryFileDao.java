package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.estore.api.estoreapi.model.Product;

public class InventoryFileDao implements InventoryDao {

    Map<Integer, Product> products;

    /**
     * *{@inheritDoc}
     */
    @Override
    public ArrayList<Product> getProducts() throws IOException {
        ArrayList<Product> productsList = new ArrayList<>();
        for(Product p : products.values()){
            productsList.add(p);
        }
        return productsList;
    }
  
}
