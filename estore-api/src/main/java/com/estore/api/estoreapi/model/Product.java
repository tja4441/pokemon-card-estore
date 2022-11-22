/**
 * @author Team E
 * This class was implemented as feature 1 of sprint 1 and has all information
 * that someone might need on an individual product
 */
package com.estore.api.estoreapi.model;
import java.util.Arrays;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class has 2 static properties the Id of the next Product to be made and
 * price hashmap that links product ID to a price
 */
public class Product {
    // Package private for tests
    static final String STRING_FORMAT = "Product [id=%d, name=%s, types=%s, quantity=%d, price=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("types") private CardType[] types;
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
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("types") CardType[] types, @JsonProperty("quantity")int quantity, @JsonProperty("price")float price) {
        this.id = id;
        this.name = name;
        this.types = types;
        this.quantity = quantity;
        BigDecimal bd = BigDecimal.valueOf(price);
        this.price = bd.floatValue();
    }

    /**
     * @return returns the id of this product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return returns the name of this product
     */
    public String getName() {
        return name;
    }

    /**gets the type array representing the array of types
     * 
     * @return a card array containing the types of the card
     */
    public CardType[] getTypes() {
        return types;
    }

    /**
     * @return returns the quantity of this product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the price of the product - necessary for JSON object to Java object deserialization
     * @param price The price of the product
     */
    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice(){
        return this.price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT,id,name,types,quantity,price);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof Product)) {
            return false;
        }
        Product otherProduct = (Product) other;
        if((this.name.toLowerCase().equals(otherProduct.name.toLowerCase())) && Arrays.equals(this.types, otherProduct.getTypes())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *{@inheritDoc}}
     */
    @Override
    public int hashCode() {
        String hashString = name + types;
        return hashString.toLowerCase().hashCode();
    }
}
