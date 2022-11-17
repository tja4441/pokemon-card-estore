package com.estore.api.estoreapi.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreStatistic {
    @JsonProperty("purchaseCount") private int purchaseCount;
    @JsonProperty("loginCount") private int loginCount;
    @JsonProperty("totalPurchaseAmt") private float totalPurchaseAmt;
    @JsonProperty("avgPurchaseAmt") private float averagePurchaseAmt;
    @JsonProperty("fiveMostPopular") private int[] mostPopularProducts;
    @JsonProperty("fiveMostExpensiveOrders") private ShoppingCart[] mostExpensiveCarts;
    @JsonProperty("mostPopularType") private CardType mostPopType;
    @JsonProperty("typeRevenue") private HashMap<CardType, Float> typeRevenue;
    @JsonProperty("totalSessionTime") private float totalSessionTime;
    @JsonProperty("avgSessionTime") private float avgSessionTime;

    /** Constructor, no Params
     *  Store Statistics always exist
     */
    public StoreStatistic() {
        this.purchaseCount = 0;
        this.loginCount = 0;
        this.totalPurchaseAmt = 0;
        this.averagePurchaseAmt = 0;
        this.mostPopularProducts = new int[5];
        this.mostExpensiveCarts = new ShoppingCart[5];
        this.mostPopType = null;
        this.typeRevenue = new HashMap<CardType, Float>();
        this.totalSessionTime = 0;
        this.avgSessionTime = 0;
    }

    /**gets the purchase count
     * 
     * @return the purchase count
     */
    public int getPurchaseCount() {
        return purchaseCount;
    }

    /**gets the total logins across the store
     * 
     * @return the total number of logins across the store
     */
    public int getLoginCount() {
        return loginCount;
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

    /**sets the purchase count to the given value
     * 
     * @param purchaseCount the value to set the purchasedCount to
     */
    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    /**sets thee total login count to the given value
     * 
     * @param loginCount the value to set the loginCount to
     */
    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
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

    /**Adds the given amount to the revenue map for the given type
     * 
     * @param type the type to add the amount to
     * @param amount the amount to add to the revenue
     */
    public void increaseTypeRevenue(CardType type, float amount) {
        this.typeRevenue.put(type, this.typeRevenue.get(type) + amount);
    }

    /**Increments the number of Total Purchases by one
     * 
     */
    public void incrementTotalPurchases() {
        this.purchaseCount++;
    }

    /**Increments the number of Total Logins by one
     * 
     */
    public void incrementLoginCounter() {
        this.loginCount++;
    }

    /**Adds Amount to totalPurchaseAmt
     * 
     * @param amount the amount to be added
     */
    public void increaseTotalPurchase(float amount) {
        this.totalPurchaseAmt += amount;
    }

    /**Adds amount to totalSessionTime
     * 
     * @param amount the amount to be added
     */
    public void increaseTotalSession(float amount) {
        this.totalSessionTime += amount;
    }

    /**Checks the given cart against the top 5 most expensive carts
     * If the cart is part of the top 5 most expensive, then it is added to the list
     * Otherwise nothing changes
     * 
     * @param cart the cart to be checked
     */
    public void checkCartAgainstMostExpensive(ShoppingCart cart) {
        ShoppingCart[] allCarts = new ShoppingCart[this.mostExpensiveCarts.length + 1];
        for(int i = 0; i < 5; i++) {
            allCarts[i] = this.mostExpensiveCarts[i];
        }
        allCarts[5] = cart;
        Arrays.sort(allCarts, new Comparator<ShoppingCart>() {
            public int compare(ShoppingCart o1, ShoppingCart o2) {
                return (int)(o2.GetTotalPrice() - o1.GetTotalPrice());
            };
        });
        for(int i = 0; i < 5; i++) {
            this.mostExpensiveCarts[i] = allCarts[i];
        }
    }
}
