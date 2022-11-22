package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.controller.InventoryController;
import com.estore.api.estoreapi.model.OrderHistory;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class ShoppingCartFileDao implements ShoppingCartDao {

    Map<Integer,ShoppingCart> carts;            // provides a local cache of the carts
    Map<Integer,OrderHistory> orders;
    private ObjectMapper objectMapper;          // converts between ShoppingCart objects and JSON text file formats
    private ObjectMapper orderMapper;
    private String cartFilename;                // Filename to read/write carts
    private String orderFilename;               // Filename to read/write orders

    /**
     * Creates a {@linkplain ShoppingCart cart} File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * 
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ShoppingCartFileDao(@Value("${carts.file}") String cartFilename, @Value("${order-history.file}") String orderFilename,ObjectMapper objectMapper) throws IOException{
        this.cartFilename = cartFilename;
        this.orderFilename = orderFilename;
        this.objectMapper = objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.orderMapper = objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();  // load the carts from the file
        loadOrderHistory();     // load the orders from the order-history file
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
     * @throws IOException
     */
    @Override
    public ShoppingCart getCart(int id, InventoryController inventoryController) throws IOException {
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
    public ShoppingCart createCart(int id) throws IOException {
        synchronized(carts){
            ShoppingCart newCart = new ShoppingCart(id);

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

        for (ShoppingCart cart : carts.values()) {
            cartArrayList.add(cart);
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
            boolean productIncremented = false;
            if (cart == null) {
                return null;
            } else {
                for (Product cartProduct : cart.getContents()) {
                    if (product.getId() == cartProduct.getId()) {
                        if (product.getQuantity() > cartProduct.getQuantity()) {
                            cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                            cart.calculateTotalPrice();
                            productIncremented = true;
                        }
                        else if(product.getQuantity() == cartProduct.getQuantity()){
                            return cart;
                        }
                        break;
                    } 
                }
                if (!productIncremented) {
                    if (product.getQuantity() < 1) {
                        return cart;
                    } else{
                        cart.addToCart(new Product(product.getId(), product.getName(), product.getTypes(), 1, product.getPrice()));
                    }
                }
                updateCart(cart);
                return cart;
            }
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public ShoppingCart deleteFromCart(int id, Product product) throws IOException {
        synchronized(carts) {
            ShoppingCart cart = carts.get(id);
            boolean productInCart = false;
            if (cart == null) {
                return null;
            } else {
                for (Product cartProduct : cart.getContents()) {
                    if (product.getId() == cartProduct.getId()) {
                        if (cartProduct.getQuantity() <= 1) {
                            cart.removeFromCart(cartProduct);
                        } else {
                            cartProduct.setQuantity(cartProduct.getQuantity() - 1);
                            cart.calculateTotalPrice();
                        }
                        productInCart = true;
                        break;
                    } 
                }
                if (productInCart) {
                    updateCart(cart);
                    return cart;
                } else {
                    return null;
                }
            }
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean refreshCart(int id,InventoryController inventoryController) throws IOException {
        synchronized(carts) {
            ShoppingCart cart = carts.get(id);
            HashSet<Product> productsSet = cart.getContents();
            Product[] products = new Product[productsSet.size()];
            productsSet.toArray(products);
            boolean nothingChanged = true;

            for (Product product : products) {
                Product invProduct = inventoryController.getProduct(product.getId()).getBody();
                if (invProduct != null && invProduct.getName().equals(product.getName()) && invProduct.getQuantity() > 0 && Arrays.equals(product.getTypes(), invProduct.getTypes())) {
                    if (invProduct.getQuantity() < product.getQuantity()) {
                        cart.updateProductInCart(product, invProduct);
                        nothingChanged = false;
                    }
                    else if (invProduct.getPrice() != product.getPrice()){
                        Product newProduct = new Product(product.getId(), product.getName(), product.getTypes(), product.getQuantity(), invProduct.getPrice());
                        cart.updateProductInCart(product, newProduct);
                        nothingChanged = false;
                    }
                } else {
                    cart.removeFromCart(product);
                    nothingChanged = false;
                }
            }
            updateCart(cart);
            return nothingChanged;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public ShoppingCart checkout(int id, InventoryController inventoryController) throws IOException {
        synchronized(carts) {
            if (refreshCart(id, inventoryController)) {
                ShoppingCart cart = carts.get(id);

                setAndSaveOrder(id, cart);
            
                HashSet<Product> productsSet = cart.getContents();
                Product[] contents = new Product[productsSet.size()];
                productsSet.toArray(contents);
    
                for (Product product : contents) {
                    Product invProduct = inventoryController.getProduct(product.getId()).getBody();
                    if(invProduct != null) {
                        invProduct.setQuantity(invProduct.getQuantity() - product.getQuantity());
                        inventoryController.updateProduct(invProduct);
                        cart.removeFromCart(product);
                    }
                }
                return updateCart(cart);
            } else {
                return null;
            }
        }
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
        ShoppingCart[] cartArray = objectMapper.readValue(new File(cartFilename),ShoppingCart[].class);

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
        objectMapper.writeValue(new File(cartFilename),cartArray);
        return true;
    }

    /**
     * Loads {@linkplain OrderHistory orders} from the orderHistory JSON file into the map
     * 
     * @return true if the file was read successfuly
     * 
     * @throws IOException when file cannot be accessed or read from
     * 
     * @author Timothy Avila
     */
    private boolean loadOrderHistory() throws IOException {
        orders = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of orders
        OrderHistory[] orderHistory = orderMapper.readValue(new File(orderFilename),OrderHistory[].class);

        // Add each order to the tree map
            for (OrderHistory order : orderHistory) {
                if (order != null) {
                    orders.put(order.getOrderNumber(), order);
                }
            }
        return true;
    }

    /**
     * Saves the {@linkplain OrderHistory orders} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link OrderHistory orders} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     * 
     * @author Timothy Avila
     */
    private boolean saveOrderHistory() throws IOException {
        OrderHistory[] ordersArray = getOrders();

        // Serializes the Java Objects to JSON objects into the file
        orderMapper.writeValue(new File(orderFilename),ordersArray);
        return true;
    }

    /**
     * Returns the {@linkplain OrderHistory orders} from the order cache
     * 
     * @return {@link OrderHistory orders} from the order cache
     * 
     * @author Timothy Avila
     */
    public OrderHistory[] getOrders() {
        ArrayList<OrderHistory> orderArrayList = new ArrayList<>();

        for (OrderHistory order : orders.values()) {
            orderArrayList.add(order);
        }

        OrderHistory[] ordersArray = new OrderHistory[orderArrayList.size()];
        orderArrayList.toArray(ordersArray);
        return ordersArray;
    }

    /**
     * Returns the {@linkplain OrderHistory orders} from the order cache with a given id
     * 
     * @param id integer corresponding to id of user who made an order
     * 
     * @return {@link OrderHistory orders} from the order cache with a given id
     * 
     * @author Timothy Avila
     */
    public OrderHistory[] searchOrders(int id) {
        ArrayList<OrderHistory> orderArrayList = new ArrayList<>();

        for (OrderHistory order : orders.values()) {
            if (order.getId() == id) {
                orderArrayList.add(order);
            } 
        }
        
        OrderHistory[] ordersArray = new OrderHistory[orderArrayList.size()];
        orderArrayList.toArray(ordersArray);
        return ordersArray;
    }

    /**
     * Returns the next available order number, increasing sequentially from 1
     * 
     * @return int corresponding to next order number
     * 
     * @author Timothy Avila
     */
    private int nextOrderNumber() {
        synchronized(orders) {
            int i = 1;
            while(orders.containsKey(i)) {
                i++;
            }
            return i;
        }
    }

    /**
     * Creates and saves an {@link OrderHistory OrderHistory} object to a JSON file for data persistence
     * 
     * @param id int corresponding to the ID of the {@link User user} that made the order.
     * 
     * @param cart the {@link ShoppingCart shopping cart} that the {@link User user} had when they placed the order.
     * 
     * @throws IOException
     */
    public void setAndSaveOrder(int id, ShoppingCart cart) throws IOException{
        synchronized(orders){
            ShoppingCart orderCart = new ShoppingCart(cart.getId());
            HashSet<Product> newContents = new HashSet<>();

            for (Product product : cart.getContents()) {
                Product newProduct = new Product(product.getId(), product.getName(), product.getTypes(), product.getQuantity(), product.getPrice());
                newContents.add(newProduct);
            }
            orderCart.setContents(newContents);
            orderCart.calculateTotalPrice();
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            String timeStamp = LocalDateTime.now().format(dtf);
            OrderHistory order = new OrderHistory(id, orderCart, nextOrderNumber(), timeStamp);
            orders.put(order.getOrderNumber(), order);
            saveOrderHistory();
        }
    }
}
