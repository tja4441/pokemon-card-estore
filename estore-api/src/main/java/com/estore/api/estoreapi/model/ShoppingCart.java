package com.estore.api.estoreapi.model;

import java.util.HashSet;

    /**
     * Class Representing a Shopping Cart
     * 
     * @author Team E
     */
public class ShoppingCart extends HashSet<Product>{
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
     * @param cartSet the set to base the Shopping Cart off of
     */
    public ShoppingCart(HashSet<Product> cartSet) {
        this.cartSet = new HashSet<Product>(cartSet);
    }

    /**
     * Getter for the set representing the cart, only for testing purposes
     * 
     * @return cartSet
     */
    public HashSet<Product> getCartSet() {
        return cartSet;
    }

    /**
     * Adds Product to Cart
     * 
     * @param product the product to add
     */
    public void addToCart(Product product) {
        this.cartSet.add(product);
    }

    /**
     * Removes Product from Cart
     * 
     * @param product the product to remove
     */
    public void removeFromCart(Product product) {
        this.cartSet.remove(product);
    }


    /**
     * Updates Old Product into New Product
     * 
     * @param oldP The Old Product
     * @param newP The New Product
     */
    public void updateProductInCart(Product oldP, Product newP) {
        this.cartSet.remove(oldP);
        this.cartSet.add(newP);
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