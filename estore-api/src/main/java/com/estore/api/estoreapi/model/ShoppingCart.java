package com.estore.api.estoreapi.model;

import java.util.HashSet;

import org.springframework.web.client.RestTemplate;

import com.estore.api.estoreapi.controller.InventoryController;

/**
 * Class Representing a Shopping Cart
 * 
 * @author Jensen Derosier
 * 
 * @author Daniel Pittman
 */
public class ShoppingCart extends HashSet<Product>{
    private HashSet<Product> cartSet;
    private float totalPrice;
    private InventoryController inventoryController;

    /**
     * Constructor with no Given Set, Returns Empty Shopping Cart
     * 
     * @author Jensen Derosier
     */
    public ShoppingCart(InventoryController inventoryController) {
        this.cartSet = new HashSet<Product>();
        this.totalPrice = 0.00f;
        this.inventoryController = inventoryController;
    }

    /**
     * Constructor with Given Set, Returns Shopping Cart with items from Set
     * 
     * @param cartSet the set to base the Shopping Cart off of
     * 
     * @author Jensen Derosier
     */
    public ShoppingCart(HashSet<Product> cartSet, InventoryController inventoryController) {
        this.cartSet = new HashSet<Product>(cartSet);
        calculateTotalPrice();
    }

    /**
     * Getter for the set representing the cart, only for testing purposes
     * 
     * @return cartSet
     * 
     * @author Jensen Derosier
     */
    public HashSet<Product> getCartSet() {
        return cartSet;
    }

    /**
     * Adds Product to Cart
     * 
     * @param product the product to add
     * 
     * @author Jensen Derosier
     */
    public void addToCart(Product product) {
        this.cartSet.add(product);
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
        this.cartSet.remove(product);
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
        this.cartSet.remove(oldP);
        this.cartSet.add(newP);
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
        return this.cartSet.size();
    }
    
    /**
     * Decrements the quantities of the {@link Product products} in the inventory system
     * 
     * @return list of the {@link ShoppingCart cart} contents that have been purchased
     * 
     * @return total price of the {@link ShoppingCart cart} just purchased
     * 
     * @return the items from the {@link ShippingCart cart} that couldn't be purchased due to lack of inventory
     * 
     * @author Daniel Pittman
     */
    public Object[] checkout() {
        Object[] returnables = new Object[3];         // This is an array that contains the different returnable objects

        Product[] cartChanges = refreshCart();        //refresh the cart to see if all prices and quantities apply
        HashSet<Product> bought = this.cartSet;

        for (Product product : cartSet) {             // For remaining items after refresh, do the checkout process
            if(product.getQuantity() - 1 < 0){
                restTemplate = 
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
        this.cartSet = new HashSet<Product>();

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
     * The method recalculates the total price of what is in the {@link ShoppingCart cart}
     * 
     * @author Daniel Pittman
     */
    public void calculateTotalPrice() {
        float newTotalPrice = 0.00f;

        for (Product product : cartSet) {
            newTotalPrice += product.getPrice();
        }

        this.totalPrice = newTotalPrice;
    }

    public Product[] refreshCart() {
        Product[] changedProducts = new Product[this.size()];
        //TODO : Create this cart refreshing method
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
        return this.cartSet.toString();
    }
}