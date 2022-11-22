package com.estore.api.estoreapi.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StoreStatistic {
    @JsonProperty("purchaseCount") private int purchaseCount;
    @JsonProperty("totalPurchaseAmt") private float totalPurchaseAmt;
    @JsonProperty("avgPurchaseAmt") private float averagePurchaseAmt;
    @JsonProperty("productPurchaseAmts") private HashMap<Integer, Integer> productPurchaseAmts;
    @JsonProperty("fiveMostPopular") private int[] mostPopularProducts;
    @JsonProperty("fiveMostExpensiveOrders") private ShoppingCart[] mostExpensiveCarts;
    @JsonProperty("mostPopularType") private CardType mostPopularType;
    @JsonProperty("typeRevenue") private HashMap<CardType, Float> typeRevenue;
    @JsonProperty("totalSessionTime") private float totalSessionTime;
    @JsonProperty("avgSessionTime") private float avgSessionTime;

    /** Constructor, no Params
     *  Store Statistics always exist
     */
    public StoreStatistic() {
        this.purchaseCount = 0;
        this.totalPurchaseAmt = 0;
        this.averagePurchaseAmt = 0;
        this.productPurchaseAmts = new HashMap<Integer, Integer>();
        this.mostPopularProducts = new int[5];
        this.mostExpensiveCarts = new ShoppingCart[5];
        this.mostPopularType = null;
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
    public CardType getMostPopularType() {
        return mostPopularType;
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
    public void setMostPopularType(CardType mostPopType) {
        this.mostPopularType = mostPopType;
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
        if (this.typeRevenue.containsKey(type)) {
            this.typeRevenue.put(type, this.typeRevenue.get(type) + amount);
        } else {
            this.typeRevenue.put(type, amount);
        }
    }

    /**Increments the number of Total Purchases by one
     * 
     */
    public void incrementTotalPurchases() {
        this.purchaseCount+= 1;
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

    /**Increments count of how many times a product has been purchased
     * 
     * @param productID the product
     * @param amount the amount to increment by
     */
    public void addProductPurchaseAmounts(int productID, int amount) {
        if (this.productPurchaseAmts.containsKey(productID)) {
            int newAmount = this.productPurchaseAmts.get(productID);
            newAmount += amount;
            this.productPurchaseAmts.put(productID, newAmount);
        } else {
            this.productPurchaseAmts.put(productID, amount);
        }
    }
    
    /**
     * Calculates the top five purchased products via the purchased amounts
     */
    public void calculateMostPopularProducts(Map<Integer,UserStatistic> usersStats) {
        Map<Integer,Integer> popularProductTally = new HashMap<Integer,Integer>();
        int[] mostPopular = new int[5];
        for (int uID : usersStats.keySet()) {
            UserStatistic userStat = usersStats.get(uID);
            if (userStat.getPurchaseCounter() == 0) {
                continue;
            }
            Integer userFav = userStat.getMostPurchased();
            if (popularProductTally.containsKey(userFav)) {
                popularProductTally.put(userFav, popularProductTally.get(userFav) + 1);
            } else {
                popularProductTally.put(userFav, 1);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (Integer pID : popularProductTally.keySet()) {
                if (mostPopular[i] == 0) {
                    mostPopular[i] = pID;
                } else if (popularProductTally.get(mostPopular[i]) < popularProductTally.get(pID)) {
                    mostPopular[i] = pID;
                }
            }
            popularProductTally.remove(mostPopular[i]);
        }

        this.mostPopularProducts = mostPopular;
    }

    public void calculateMostPopularType(Map<Integer,UserStatistic> usersStats){
        Map<CardType,Integer> popularTally = new HashMap<CardType,Integer>();
        CardType mostPopular = null;
        for (int uID : usersStats.keySet()) {
            UserStatistic userStat = usersStats.get(uID);
            CardType userFav = userStat.getMostPopularType();
            if (popularTally.containsKey(userFav)) {
                popularTally.put(userFav, popularTally.get(userFav) + 1);
            } else {
                popularTally.put(userFav, 1);
            }
        }

        for (CardType type : popularTally.keySet()) {
            if (mostPopular == null) {
                mostPopular = type;
            } else if (popularTally.get(mostPopular) < popularTally.get(type)) {
                mostPopular = type;
            }
        }
        this.mostPopularType = mostPopular;
    }

    public void calculateAveragePurchaseAmount() {
        this.averagePurchaseAmt = (this.totalPurchaseAmt / this.purchaseCount);
    }

    public void calculateAverageSessionTime() {
        incrementTotalPurchases();
        this.avgSessionTime = this.totalSessionTime / this.purchaseCount;
    }
}
