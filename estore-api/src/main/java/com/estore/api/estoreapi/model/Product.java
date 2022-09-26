/**
 * @author Team E
 * This class was implemented as feature 1 of sprint 1 and has all information
 * that someone might need on an individual product
 */
package com.estore.api.estoreapi.model;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has 2 static properties the Id of the next Product to be made and
 * price hashmap that links product ID to a price
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());
    // Package private for tests
    static final String STRING_FORMAT = "Product [id=%d, name=%s, quantity=%j, price=%p]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("price") private float price;
    
    /**
     * Each time the class is instatiated it increments the internal ID counter by 1
     * ID is by default instatiated at 0 but in order to have persistance if the 
     * server shuts down there is a static setID function that lets it continue
     * where it left off
     * @param name the name of the product
     * @param quantity the amount of an item there is
     * @param price the price of an item
     */
    public Product(@JsonProperty("id") int id,@JsonProperty("name") String name, @JsonProperty("quantity")int quantity, @JsonProperty("price")float price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * This constructor "takes"(does not actually change other products quantity)
     * a product from an existing product(from the inventory)
     * @param product an existing product(from the inventory)
     * @param amount the amount of product that you are "taking" from the other product
     */
    public Product(Product product, int amount){
        this.id = product.id;
        this.name = product.name;
        this.quantity = amount;
        //TODO: Determine if this is necessary
    }
    
    /**
     * @return returns the id of this product
     */
    public int getId() {
        return id;
    }

    /**
     * @return returns the name of this product
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the quantity of this product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity Sets the quantity of the product to the amount passed in
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @param quantity increments the quantity of the product to the amount passed in
     */
    public void changeQuantity(int quantity) {
        this.quantity += quantity;
    }

    public float getPrice(){
        return this.price;
    }

    /**
     * returns a string representation of the product
     */
    @Override
    public String toString(){
        String output = "Product{" +
            "id: " + this.id + "\n" +
            "name: " + this.name + "\n" +
            "quantity: " + this.quantity + "\n" + "}";
        return output;
    }

    /**
     * returns true if 2 Products have the same ID
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof Product)) {
            return false;
        }
        Product otherProduct = (Product) other;
        if(this.id == otherProduct.id) {
            return true;
        }
        else {
            return false;
        }
    }
}
