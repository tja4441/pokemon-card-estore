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
    public Product[] getProducts() throws IOException {
        ArrayList<Product> productsArrayList = new ArrayList<>();
        for(Product p : products.values()){
            productsArrayList.add(p);
        }
        Product[] productsArray = new Product[productsArrayList.size()];
        productsArrayList.toArray(productsArray);
        return productsArray;
    }
  
}
