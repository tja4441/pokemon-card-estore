package com.estore.api.estoreapi.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import ch.qos.logback.core.subst.Token.Type;

public class UserStatistic {
    static final String STRING_FORMAT = "UserStatistic [id=%d, contents=%s, totalPrice=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("loginCounter")private int loginCounter;
    @JsonProperty("purchaseCounter")private int purchaseCounter;
    @JsonProperty("lifetimeSpending")private float lifetimeSpending;
    @JsonProperty("averagePurchase")private float averagePurchaseAmt;
    @JsonProperty("purchasedCounts")private HashMap<Product,Integer> purchasedCounts;
    @JsonProperty("mostPurchased")private Product mostPurchased;
    @JsonProperty("mostExpensiveOrder")private ShoppingCart mostExpensiveOrder;
    @JsonProperty("typeCounts")private HashMap<Type,Integer> typeCounts;
    @JsonProperty("typeRevenues")private HashMap<Type,Float> typeRevenues;
    @JsonProperty("averageSessionTime")private Float averageSessionTime;

    /**
     * This is the constructor for user Statistics. A user Statistic is created upon user creation
     * 
     * @param id The id of the user the statistic points to
     * 
     * @author Daniel Pittman
     */
    public UserStatistic(@JsonProperty("id") int id) {
        this.id = id;
        this.loginCounter = 0;
        this.purchaseCounter = 0;
        this.lifetimeSpending = 0.0f;
        this.averagePurchaseAmt = 0.0f;
        this.purchasedCounts = new HashMap<>();
        this.typeCounts = new HashMap<>();
        this.typeRevenues = new HashMap<>();
        this.averageSessionTime = 0.0f;
        this.mostExpensiveOrder = null;
        this.mostPurchased = null;
    }

    public int getId() {return this.id;}

    public int getLoginCounter(){return this.loginCounter;}

    public int getPurchaseCounter(){return this.purchaseCounter;}

    public float getLifetimeSpending(){return this.lifetimeSpending;}

    public float getAveragePurchase(){return this.averagePurchaseAmt;}

    public HashMap<Product,Integer> getPurchasedCounts(){return this.purchasedCounts;}

    public HashMap<Type,Integer> getTypeCounts(){return this.typeCounts;}

    public HashMap<Type,Float> getTypeRevenues(){return this.typeRevenues;}

    public Float getAverageSessionTime(){return this.averageSessionTime;}

    public ShoppingCart getMostExpensiveOrder(){return this.mostExpensiveOrder;}

    public Product getMostPurchased(){return this.mostPurchased;}

    



}
