package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.Map;
import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryFileDao implements InventoryDao {
    Map<Integer, Product> products;
    private String filename;
    private ObjectMapper objectMapper;

    /**
     * Creates a Inventory File Dao
     * 
     * @param filename Filename to Read and Write
     * @param objectMapper Provides JSON <-> Java Object
     */
    public InventoryFileDao(String filename, ObjectMapper objectMapper) {
        this.filename = filename;
        this.objectMapper = objectMapper;
    }

    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            products.put(product.getId(), product);
            return product;
        }
    }
}
