package com.estore.api.estoreapi.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreStatistic {
    @JsonProperty("avgPurchaseAmt") private float averagePurchaseAmt;
    @JsonProperty("fiveMostPopular") private int[] mostPopularProducts;
    @JsonProperty("fiveMostExpensiveOrders") private ShoppingCart[] mostExpensiveCarts;
    @JsonProperty("mostPopularType") private CardType mostPopType;
    @JsonProperty("typeRevenue") private HashMap<CardType, Float> typeRevenue;
    @JsonProperty("avgSessionTime") private float avgSessionTime;

    /** Constructor, no Params
     *  Store Statistics always exist
     */
    public StoreStatistic() {
        this.averagePurchaseAmt = 0;
        this.mostPopularProducts = new int[5];
        this.mostExpensiveCarts = new ShoppingCart[5];
        this.mostPopType = null;
        this.typeRevenue = new HashMap<CardType, Float>();
        this.avgSessionTime = 0;
    }

    /**Retreives the Average Purchase Amount
     * 
     * @return float averagePurchaseAmount for the entire store
     */
    public float getAveragePurchaseAmt() {
        return averagePurchaseAmt;
    }

    /**Retreives the 5 most popular products
     * 
     * @return int[] mostPopularProducts for the entire store
     */
    public int[] getMostPopularProducts() {
        return mostPopularProducts;
    }

    /**Retreives the 5 most expensive carts
     * 
     * @return Shoppingcart[] of the most expensive carts
     */
    public ShoppingCart[] getMostExpensiveCarts() {
        return mostExpensiveCarts;
    }

    /**Retreives the most popular type
     * 
     * @return CardType representing the most popular type
     */
    public CardType getMostPopType() {
        return mostPopType;
    }

    /**Retreives the Average Purchase Amount
     * 
     * @return HashMap<CardType, Float> a hashmap where CardTypes map to the revenue they have generated
     */
    public HashMap<CardType, Float> getTypeRevenue() {
        return typeRevenue;
    }

    /**Retreives the Avg Session Time
     * 
     * @return float representing the average session time of the users
     */
    public float getAvgSessionTime() {
        return avgSessionTime;
    }

    /**Sets the averagePurchaseAmt to the provided Value
     * 
     * @param averagePurchaseAmt the value to set the AvgPA
     */
    public void setAveragePurchaseAmt(float averagePurchaseAmt) {
        this.averagePurchaseAmt = averagePurchaseAmt;
    }

    /**Sets the mostPopularProducts to the provided Value
     * 
     * @param mostPopularProducts the value to set the mPP
     */
    public void setMostPopularProducts(int[] mostPopularProducts) {
        this.mostPopularProducts = mostPopularProducts;
    }

    /**Sets the mostExpensiveCarts to the provided Value
     * 
     * @param mostExpensiveCarts the value to set the mEC
     */
    public void setMostExpensiveCarts(ShoppingCart[] mostExpensiveCarts) {
        this.mostExpensiveCarts = mostExpensiveCarts;
    }

    /**Sets the mostPopularType
     * 
     * @param mostPopType the value to set the mPT
     */
    public void setMostPopType(CardType mostPopType) {
        this.mostPopType = mostPopType;
    }

    /**Sets the typeRevenue map
     * 
     * @param typeRevenue the value to set the tRM
     */
    public void setTypeRevenue(HashMap<CardType, Float> typeRevenue) {
        this.typeRevenue = typeRevenue;
    }

    /**Sets the avgSessionTime
     * 
     * @param avgSessionTime the value to set the aST
     */
    public void setAvgSessionTime(float avgSessionTime) {
        this.avgSessionTime = avgSessionTime;
    }
}
