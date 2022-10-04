package com.estore.api.estoreapi.model;

import java.util.HashSet;

    /**
     * Class Representing a Shopping Cart
     * 
     * @author Team E
     */
public class ShoppingCart {
    private HashSet<Product> cartSet;

    /**
     * Constructor with no Given Set, Returns Empty Shopping Cart
     */
    public ShoppingCart() {
        this(new HashSet<Product>());
    }

    /**
     * Constructor with Given Set, Returns Shopping Cart with items from Set
     * 
     * @param cartSet
     */
    public ShoppingCart(HashSet<Product> cartSet) {
        this.cartSet = cartSet;
    }

    /**
     * Adds Product to Cart
     * 
     * @param product
     */
    public void addToCart(Product product) {
        this.cartSet.add(product);
    }

    /**
     * Removes Product from Cart
     * 
     * @param product
     */
    public void removeFromCart(Product product) {
        this.cartSet.remove(product);
    }

    public void updateProductInCart(Product product) {
        this.cartSet.remove(product);
        this.cartSet.add(product);
    }

    /**
     * Gets Num of Products of Cart
     * 
     * @return Num Items in Cart
     */
    public int size() {
        return this.cartSet.size();
    }
    

    public void checkout() {
        //TODO: Checkout Method
    }

    /**
     * Formats Shopping Cart as a String
     * 
     * @return String format of Shopping Cart
     */
    @Override
    public String toString() {
        return this.cartSet.toString();
    }
}