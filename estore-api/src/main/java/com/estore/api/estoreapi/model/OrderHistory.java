package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class Representing an Order History object
 * 
 * @author Timothy Avila
 */
public class OrderHistory {

    private static final Logger LOG = Logger.getLogger(OrderHistory.class.getName());
    // Package private for tests
    static final String STRING_FORMAT = "OrderHistory [id=%d, purchasedCart=%s, orderNumber=%d, timeStamp=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("purchasedCart") private ShoppingCart purchasedCart;
    @JsonProperty("orderNumber") private int orderNumber;
    @JsonProperty("timeStamp") private String timeStamp; // string formatted as "MM/dd/yyyy HH:mm:ss" to be parsed later

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
     * A Getter method for the id of the {@link OrderHistory OrderHistory} of a {@link User user}.
     * 
     * @return id
     * 
     * @author Timothy Avila
     */
    public int getId() {
        return this.id;
    }

    /**
     * A Getter method for the cart of purchased products in the {@link OrderHistory OrderHistory} of a {@link User user}.
     * 
     * @return {@link ShoppingCart ShoppingCart} containing all items the user purchased
     * 
     * @author Timothy Avila
     */
    public ShoppingCart getCart() {
        return this.purchasedCart;
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

    /**
     * A Getter method for the time of purchase of an Order.
     * 
     * @return {@link String String} containing the time of an order, formatted as MM/dd/yyyy HH:mm:ss
     * 
     * @author Timothy Avila
     */
    public String getTime() {
        return this.timeStamp;
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
        return String.format(STRING_FORMAT, id, purchasedCart, orderNumber, timeStamp);
    }
}
