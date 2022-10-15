package com.estore.api.estoreapi.model;

import java.util.HashSet;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Class Representing a Shopping Cart
 * 
 * @author Jensen Derosier
 * 
 * @author Daniel Pittman
 */
public class ShoppingCart {
    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());
    // Package private for tests
    static final String STRING_FORMAT = "ShoppingCart [id=%d, contents=%s, totalPrice=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("contents") private HashSet<Product> contents;
    @JsonProperty("totalPrice") private float totalPrice;

    /**
     * Constructor with no Given Set and only an id, Returns Empty Shopping Cart
     * 
     * @author Jensen Derosier
     */
    public ShoppingCart(@JsonProperty("id") int id) {
        this.id = id;
        this.contents = new HashSet<Product>();
        this.totalPrice = 0.00f;
    }

    /**
     * Constructor with Given Set and id, Returns Shopping Cart with items from Set
     * 
     * @param id the identification of the shopping cart, which should be the same id as the user it belongs to
     * 
     * @param contents the set to base the Shopping Cart off of
     * 
     * @author Jensen Derosier
     */
    public ShoppingCart(@JsonProperty("id") int id, @JsonProperty("contents") HashSet<Product> contents) {
        this.id = id;
        this.contents = contents;
        calculateTotalPrice();
    }

    /**
     * Getter for the set representing the cart
     * 
     * @return cartSet
     * 
     * @author Jensen Derosier
     */
    public HashSet<Product> getContents() {
        return contents;
    }

    /**
     * sets the contents of the cart - necessary for JSON object to Java object deserialization
     * @param contents the contents being set as the carts only contents
     */
    public void setContents(HashSet<Product> contents) {
        this.contents = contents;
    }

    /**
     * @return the id of the cart, which also is the id of the user the cart belongs to
     */
    public int getId() {
        return id;
    }

    /**
     * Adds Product to Cart
     * 
     * @param product the product to add
     * 
     * @author Jensen Derosier
     */
    public void addToCart(Product product) {
        this.contents.add(product);
        calculateTotalPrice();
    }

    /**
     * Removes Product from Cart
     * 
     * @param product the product to remove
     * 
     * @author Jensen Derosier
     */
    public void removeFromCart(Product product) {
        this.contents.remove(product);
        calculateTotalPrice();
    }


    /**
     * Updates Old Product into New Product
     * 
     * @param oldP The Old Product
     * 
     * @param newP The New Product
     * 
     * @author Jensen Derosier
     */
    public void updateProductInCart(Product oldP, Product newP) {
        this.contents.remove(oldP);
        this.contents.add(newP);
        calculateTotalPrice();
    }

    /**
     * Gets Num of Products of Cart
     * 
     * @return Num Items in Cart
     * 
     * @author Jensen Derosier
     */
    public int size() {
        return this.contents.size();
    }
    
    /**
     * Decrements the quantities of the {@link Product products} in the inventory system
     * 
     * @return list of the {@link ShoppingCart cart} contents that have been purchased
     * 
     * @return total price of the {@link ShoppingCart cart} just purchased
     * 
     * @return the items from the {@link ShoppingCart cart} that couldn't be purchased due to lack of inventory
     * 
     * @author Daniel Pittman
     */
    public Object[] checkout() { //TODO: redo this checkout method
        Object[] returnables = new Object[3];         // This is an array that contains the different returnable objects

        Product[] cartChanges = refreshCart();        //refresh the cart to see if all prices and quantities apply
        HashSet<Product> bought = this.cartSet;

        for (Product product : cartSet) {             // For remaining items after refresh, do the checkout process
            if(product.getQuantity() - 1 < 0){
                inventoryController.updateProduct(new Product(product.getId(), product.getName(),0,product.getPrice()));
            }
            else{
                inventoryController.updateProduct(new Product(product.getId(), product.getName(),product.getQuantity() - 1,product.getPrice()));
            }
        }

        // prepare the method returnables for being returned in a single object array
        returnables[0] = bought;
        returnables[1] = this.GetTotalPrice();
        returnables[2] = cartChanges;

        // wipe the contents of the cart now that checkout has been completed
        this.contents = new HashSet<Product>();
        calculateTotalPrice();

        return returnables;
    }

    /**
     * A Getter method for the total price of all {@link Product products} in the {@link ShoppingCart cart}
     * 
     * @return total price
     * 
     * @author Daniel Pittman
     */
    public float GetTotalPrice() {
        return this.totalPrice;
    }

    /**
     * Sets the total price of the cart - necessary for JSON object to Java object deserialization
     * 
     * @param totalPrice The totalPrice of the cart
     * 
     * @author Daniel Pittman
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * The method recalculates the total price of what is in the {@link ShoppingCart cart}
     * 
     * @author Daniel Pittman
     */
    public void calculateTotalPrice() {
        float newTotalPrice = 0.00f;

        for (Product product : contents) {
            newTotalPrice += product.getPrice();
        }

        this.totalPrice = newTotalPrice;
    }

    public Product[] refreshCart() {
        Product[] changedProducts = new Product[this.size()];
        
       // for (Product product : Products) {
            
       //TODO: Make the cart have refresh capabilities
        return changedProducts;
    }

    /**
     * Formats Shopping Cart as a String
     * 
     * @return String format of Shopping Cart
     * 
     * @author Jensen Derosier
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id,contents,totalPrice);
    }

    /**
     * Determines if two carts are equal based on their IDs
     * 
     * @return true if equal, false otherwise
     * 
     * @author Daniel Pittman
     */
    public boolean equals(Object other) {
        if (!(other instanceof ShoppingCart)) {
            return false;
        }
        ShoppingCart otherCart = (ShoppingCart) other;
        if (this.id == otherCart.id) {
            return true;
        } else {
            return false;
        }
    }
}