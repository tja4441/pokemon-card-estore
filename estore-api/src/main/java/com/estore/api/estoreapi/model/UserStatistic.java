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
    @JsonProperty("LifetimeSessionTime")private float lifetimeSessionTime;
    @JsonProperty("averagePurchase")private float averagePurchaseAmt;
    @JsonProperty("purchasedCounts")private HashMap<Product,Integer> purchasedCounts;
    @JsonProperty("mostPurchased")private Product mostPurchased;
    @JsonProperty("mostExpensiveOrder")private ShoppingCart mostExpensiveOrder;
    @JsonProperty("typeCounts")private HashMap<Type,Integer> typeCounts;
    @JsonProperty("typeRevenues")private HashMap<Type,Float> typeRevenues;
    @JsonProperty("mostPopularType")private Type mostPopularType;
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
        this.lifetimeSessionTime = 0.0f;
        this.averagePurchaseAmt = 0.0f;
        this.purchasedCounts = new HashMap<>();
        this.typeCounts = new HashMap<>();
        this.typeRevenues = new HashMap<>();
        this.averageSessionTime = 0.0f;
        this.mostExpensiveOrder = null;
        this.mostPurchased = null;
        this.mostPopularType = null;
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

    public float getLifetimeSessionTime(){return this.lifetimeSessionTime;}

    public Type getMostPopularType(){return this.mostPopularType;}

    public void setId(int id) {this.id = id;}

    public void setLoginCounter(int loginCounter){this.loginCounter = loginCounter;}

    public void setPurchaseCounter(int purchaseCounter){this.purchaseCounter = purchaseCounter;}

    public void setLifetimeSpending(float lifetimeSpending){this.lifetimeSpending = lifetimeSpending;}

    public void setAveragePurchase(float averagePurchaseAmt){this.averagePurchaseAmt = averagePurchaseAmt;}

    public void setPurchasedCounts(HashMap<Product,Integer> purchasedCounts){this.purchasedCounts = purchasedCounts;}

    public void setTypeCounts(HashMap<Type,Integer> typeCounts){this.typeCounts = typeCounts;}

    public void setTypeRevenues(HashMap<Type,Float> typeRevenues){this.typeRevenues = typeRevenues;}

    public void setAverageSessionTime(float averageSessionTime){this.averageSessionTime = averageSessionTime;}

    public void setMostExpensiveOrder(ShoppingCart mostExpensiveOrder){this.mostExpensiveOrder = mostExpensiveOrder;}

    public void setMostPurchased(Product mostPurchased){this.mostPurchased = mostPurchased;}

    public void setLifetimeSessionTime(float lifetimeSessionTime){this.lifetimeSessionTime = lifetimeSessionTime;}

    public void setMostPopularType(Type mostPopularType){this.mostPopularType = mostPopularType;}

    public void incrementLoginCounter(){
        // TODO this
    }

    public void incrementPurchaseCounter(){
        //TODO this
    }

    public void incrementLifetimeAmount(float lifetimeIncrease){
        //TODO this
    }

    public void increaseTypeTally(Type type, int amount){
        //TODO make this
    }

    public void increaseTypeRevenue(Type type, float amount){
        //TODO Make this
    }

    public void increaseProductTally(Product[] products){
        //TODO make this
    }

    public void calculateAveragePurchaseAmount(){
        //TODO make this
    }

    public void determineMostPurchasedProduct(){
        //TODO make this
    }

    public void determineMostExpensiveOrder(){
        //TODO make this
    }

    public void calculateAverageSessionTime(){
        //TODO make this
    }

    public void determineMostPopularType(){
        //TODO make this
    }

    @Override
    public String toString(){
        return ""; //TODO make a toString
    }

    @Override
    public boolean equals(Object other){
        return true; //TODO make an equals method
    }

    @Override
    public int hashCode(){
        return 0; //TODO make a hashcode method
    }
}
