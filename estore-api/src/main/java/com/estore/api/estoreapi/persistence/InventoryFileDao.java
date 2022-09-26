package com.estore.api.estoreapi.persistence;
import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;

import java.io.File;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class InventoryFileDao implements InventoryDao {

    Map<Integer,Product> products;      // provides a local cache of the product objects
    ObjectMapper objectMapper;          //Converts between Hero objects and JSON text file formats
    String filename;                    //Filename to read/write
    private static final Logger LOG = Logger.getLogger(InventoryFileDao.class.getName());
    private static int nextId;  // The next Id to assign to a new product

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public InventoryFileDao(@Value("${inventory.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the products from the file
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map
     * 
     * @return  The array of {@link Product products}, may be empty
     */
    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map for any
     * {@linkplain Product products} that contains the text specified by containsText
     * 
     * If containsText is null, the array contains all of the {@linkplain Product products}
     * in the tree map
     * 
     * @return  The array of {@link Product products}, may be empty
     */
    private Product[] getProductsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized(products) {
            if(products.containsKey(id)){
                products.remove(id);
                nextId--;
                return save();
            }
            else{
                return false;
            }
        }
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Product products} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();

        // Serializes the Java Objects to JSON objects into the file
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    /**
     * Loads {@linkplain Hero heroes} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        Product[] productArray = objectMapper.readValue(new File(filename),Product[].class);

        // Add each hero to the tree map and keep track of the greatest id
        for (Product product : productArray) {
            products.put(product.getId(),product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

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

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(products) {
            if (products.containsKey(product.getId()) == false)
                return null;  // product does not exist

            products.put(product.getId(),product);
            save(); // may throw an IOException
            return product;
        }
    }

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

    /**
    ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(products) {
            Product newProduct = new Product(nextId(),product.getName(),
                                     product.getQuantity(),product.getPrice());
            products.put(newProduct.getId(), newProduct);
            save();
            return newProduct;
        }
    }

    @Override
    public Product[] findProducts(String subString) throws IOException {
        synchronized(products) {
            Product[] productArray = products.values().stream()
                .filter(product -> subString == null || product.getName().contains(subString))
                .toArray(size -> new Product[size]);
            return productArray;
        }
    }
}

