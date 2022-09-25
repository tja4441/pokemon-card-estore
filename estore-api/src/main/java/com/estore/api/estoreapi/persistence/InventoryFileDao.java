package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryFileDao implements InventoryDao {
    Map<Integer, Product> products;
    private ObjectMapper objectMapper;
    private String filename;

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

    /**
     * 
     */
    public Product[] getProductsArray() {
        ArrayList<Product> productList = new ArrayList<>();

        for(Product Product : products.values()) {
            productList.add(Product);
        }
        
        Product[] productArray = new Product[productList.size()];
        productList.toArray(productArray);
        return productArray;
    }

    /**
     * 
     * @return True if the objects were properly saved
     */
    public Boolean save(){
        Product[] productArray = getProductsArray();

        try {
            objectMapper.writeValue(new File(filename), productArray);
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    @Override
    public Product getProduct(int id) throws IOException {
        synchronized(products) {
            if(products.containsKey(id)) {
                return products.get(id);
            }
            else {
                return null;
            }
        }
    }

    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            products.put(product.getId(), product);
            save();
            return product;
        }
    }
}
