package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.controller.InventoryController;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class ShoppingCartFileDao implements ShoppingCartDao {

    Map<Integer,ShoppingCart> carts;            // provides a local cache of the carts
    private ObjectMapper objectMapper;          // converts between ShoppingCart objects and JSON text file formats
    private String filename;                    // Filename to read/write
    private static final Logger LOG = Logger.getLogger(ShoppingCartFileDao.class.getName());

    /**
     * Creates a {@linkplain ShoppingCart cart} File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * 
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ShoppingCartFileDao(@Value("${carts.file}") String filename,ObjectMapper objectMapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();  // load the carts from the file
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public ShoppingCart updateCart(ShoppingCart cart) throws IOException {
        synchronized(carts) {
            if(carts.containsKey(cart.getId()) == false){
                return null;
            }
            carts.put(cart.getId(), cart);
            save(); // may throw an IOException
            return cart;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public ShoppingCart getCart(int id) {
        synchronized(carts){
            if(carts.containsKey(id)){
                return carts.get(id);
            }else {
                return null;
            }
        }               
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public ShoppingCart createCart(ShoppingCart cart) throws IOException {
        synchronized(carts){
            ShoppingCart newCart = new ShoppingCart(cart.getId());

            for (ShoppingCart other : carts.values()) {
                if(newCart.equals(other)){
                    return null;
                }
            }

            carts.put(newCart.getId(), newCart);
            save();
            return newCart;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteCart(int id) throws IOException {
        synchronized(carts) {
            if (carts.containsKey(id)) {
                carts.remove(id);
                return save();
            }
            else {
                return false;
            }
        }

    }

    /**
     * Generates an array of {@linkplain ShoppingCart carts} from the tree map for all {@link ShoppingCart carts}
     * 
     * @return The array of {@link ShoppingCart carts}, may be empty
     * 
     * @author Daniel Pittman
     */
    private ShoppingCart[] getCartsArray() {
        ArrayList<ShoppingCart> cartArrayList = new ArrayList<>();

        for (ShoppingCart product : carts.values()) {
            cartArrayList.add(product);
        }

        ShoppingCart[] cartArray = new ShoppingCart[cartArrayList.size()];
        cartArrayList.toArray(cartArray);
        return cartArray;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public ShoppingCart addToCart(int id, Product product) throws IOException {
        synchronized(carts) {
            ShoppingCart cart = carts.get(id);
            cart.addToCart(product);
            carts.put(cart.getId(), cart);
            save();
            return cart;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public ShoppingCart deleteFromCart(int id, Product product) throws IOException {
        synchronized(carts) {
            ShoppingCart cart = carts.get(id);
            cart.removeFromCart(product);
            carts.put(cart.getId(), cart);
            save();
            return cart;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean refreshCart(int id,InventoryController inventoryController) throws IOException {
        synchronized(carts) {
            ShoppingCart cart = carts.get(id);
            Product[] contents = (Product[]) cart.getContents().toArray();
            boolean nothingDeleted = true;

            for (Product product : contents) {
                Product updatedProduct = inventoryController.getProduct(product.getId()).getBody();
                Product newProduct = new Product(product, updatedProduct.getId());

                if (updatedProduct.getQuantity() == 0 || !product.getName().equals(updatedProduct.getName()) || updatedProduct == null) {
                    cart.removeFromCart(product);
                    nothingDeleted = false;
                } else {
                    if (updatedProduct.getQuantity() != product.getQuantity()) {
                        newProduct.setQuantity(updatedProduct.getQuantity());
                    }
                }
                if (product.getPrice() != updatedProduct.getPrice() && updatedProduct != null) {
                    newProduct.setPrice(updatedProduct.getPrice());
                }
                cart.updateProductInCart(product, newProduct);
            }
            carts.put(cart.getId(), cart);
            save();
            return nothingDeleted;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public ShoppingCart checkout(int id, InventoryController inventoryController) throws IOException {
        if (refreshCart(id, inventoryController)) {
            ShoppingCart cart = carts.get(id);
            Product[] contents = (Product[]) cart.getContents().toArray();

            for (Product product : contents) {
                Product invProduct = inventoryController.getProduct(product.getId()).getBody();
                if (invProduct.getQuantity() - 1 < 0) {
                    invProduct.setQuantity(0);
                } else {
                    //TODO: Keep working on this
                }
            }
            
        } else {
            return null;
        }
        return null;
    }

    /**
     * Loads {@linkplain ShoppingCart carts} from the JSON file into the map
     * 
     * @return true if the file was read successfuly
     * 
     * @throws IOException when file cannot be accessed or read from
     * 
     * @author Daniel Pittman
     */
    private boolean load() throws IOException {
        carts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of carts
        ShoppingCart[] cartArray = objectMapper.readValue(new File(filename),ShoppingCart[].class);

        // Add each product to the tree map
        for (ShoppingCart shoppingCart : cartArray) {
            carts.put(shoppingCart.getId(), shoppingCart);
        }
        return true;
    }

    /**
     * Saves the {@linkplain ShoppingCart carts} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link ShoppingCart carts} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     * 
     * @author Daniel Pittman
     */
    private boolean save() throws IOException {
        ShoppingCart[] cartArray = getCartsArray();

        // Serializes the Java Objects to JSON objects into the file
        objectMapper.writeValue(new File(filename),cartArray);
        return true;
    }
}
