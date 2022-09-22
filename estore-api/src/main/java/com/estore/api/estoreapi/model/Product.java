package com.estore.api.estoreapi.model;

public class Product {
    static int ID = 0;
    private String name;
    private int quantity;
    private int id;
    private float price;
    
    public Product(String name, int quantity, float price) {
        this.id = ID++;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
