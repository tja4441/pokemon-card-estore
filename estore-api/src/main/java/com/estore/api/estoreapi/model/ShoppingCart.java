package com.estore.api.estoreapi.model;

import java.util.HashSet;

public class ShoppingCart {
    private HashSet<Product> cartSet;

    public ShoppingCart() {
        this(new HashSet<Product>());
    }
    public ShoppingCart(HashSet<Product> cartSet) {
        this.cartSet = cartSet;
    }

    public void addToCart(Product product) {
        this.cartSet.add(product);
    }
    public void removeFromCart(Product product) {
        this.cartSet.remove(product);
    }

    public int size() {
        return this.cartSet.size();
    }
}
