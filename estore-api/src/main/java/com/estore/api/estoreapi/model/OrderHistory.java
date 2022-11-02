package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class Representing an Order History object
 * 
 * @author Timothy Avila
 */
public class OrderHistory {

    // Package private for tests
    static final String STRING_FORMAT = "OrderHistory [orderNumber=%d, timeStamp=%s, purchasedCart=%s, id=%d]";

    @JsonProperty("orderNumber") private int orderNumber;
    @JsonProperty("timeStamp") private String timeStamp; // string formatted as "MM/dd/yyyy HH:mm:ss" to be parsed later
    @JsonProperty("purchasedCart") private ShoppingCart purchasedCart;
    @JsonProperty("id") private int id;

     /**
     * Constructor with all required parameters, returns an OrderHistory object
     * 
     * @author Timothy Avila
     */
    public OrderHistory(@JsonProperty("id") int id, @JsonProperty("purchasedCart") ShoppingCart purchasedCart, @JsonProperty("orderNumber") int orderNumber, @JsonProperty("timeStamp") String timeStamp) {
        this.id = id;
        this.purchasedCart = purchasedCart;
        this.orderNumber = orderNumber;
        this.timeStamp = timeStamp;
    }

    /**
     * A Getter method for the {@link User user's} id that made this order.
     * 
     * @return id of the {@link User user} that made this order.
     * 
     * @author Timothy Avila
     */
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * A Getter method for the cart of purchased products in the {@link OrderHistory OrderHistory} of a {@link User user}.
     * 
     * @return {@link ShoppingCart ShoppingCart} containing all items the user purchased
     * 
     * @author Timothy Avila
     */
    public ShoppingCart getPurchasedCart() {
        return this.purchasedCart;
    }

    public void setShoppingCart(ShoppingCart cart){
        this.purchasedCart = cart;
    }

    /**
     * A Getter method for the number of this order.
     * 
     * @return positive int corresponding to order number
     * 
     * @author Timothy Avila
     */
    public int getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(int orderNumber){
        this.orderNumber = orderNumber;
    }

    /**
     * A Getter method for the time of purchase of an Order.
     * 
     * @return {@link String String} containing the time of an order, formatted as MM/dd/yyyy HH:mm:ss
     * 
     * @author Timothy Avila
     */
    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Formats OrderHistory as a String
     * 
     * @return String format of OrderHistory
     * 
     * @author Timothy Avila
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, orderNumber, timeStamp, purchasedCart, id);
    }
}
