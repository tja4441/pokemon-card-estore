package com.estore.api.estoreapi.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserStatistic {
    static final String STRING_FORMAT = "UserStatistic [id=%d, purchaseCounter=%d, lifetimeSpending=%f, lifetimeSessionTime=%f, averagePurchase=%f, purchasedCounts=%s, mostPurchased=%d, mostExpensiveOrder=%s, typeCounts=%s, typeRevenues=%s, mostPopularType=%s, averageSessionTime=%f]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("purchaseCounter")private int purchaseCounter;
    @JsonProperty("lifetimeSpending")private float lifetimeSpending;
    @JsonProperty("lifetimeSessionTime")private float lifetimeSessionTime;
    @JsonProperty("averagePurchase")private float averagePurchaseAmt;
    @JsonProperty("purchasedCounts")private HashMap<Integer,Integer> purchasedCounts;
    @JsonProperty("mostPurchased")private int mostPurchased;
    @JsonProperty("mostExpensiveOrder")private ShoppingCart mostExpensiveOrder;
    @JsonProperty("typeCounts")private HashMap<CardType,Integer> typeCounts;
    @JsonProperty("typeRevenues")private HashMap<CardType,Float> typeRevenues;
    @JsonProperty("mostPopularType")private CardType mostPopularType;
    @JsonProperty("averageSessionTime")private Float averageSessionTime;

    /**
     * This is the constructor for user Statistics. A user Statistic is created upon user creation
     * 
     * @param id The id of the user the statistic points to
     * 
     * @author Daniel Pittman
     */
    public UserStatistic(@JsonProperty("id") int id, @JsonProperty("username") String username) {
        this.id = id;
        this.username = username;
        this.purchaseCounter = 0;
        this.lifetimeSpending = 0.0f;
        this.lifetimeSessionTime = 0.0f;
        this.averagePurchaseAmt = 0.0f;
        this.purchasedCounts = new HashMap<>();
        this.typeCounts = new HashMap<>();
        this.typeRevenues = new HashMap<>();
        this.averageSessionTime = 0.0f;
        this.mostExpensiveOrder = new ShoppingCart(id);
        this.mostPurchased = -1;
        this.mostPopularType = null;
    }

    /**
     * getter for the id of the User statistic
     * 
     * @return id of the user the statistic belongs to
     */
    public int getId() {return this.id;}

    /**gets the username of the user
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for the purchaseCounter
     * 
     * @return purchaseCounter that tells the number of times the user has made a purchase
     */
    public int getPurchaseCounter(){return this.purchaseCounter;}

    /**
     * getter for the lifetimeSpending
     * 
     * @return lifetimeSpending that tells how much a user has spent in account lifetime
     */
    public float getLifetimeSpending(){return this.lifetimeSpending;}

    /**
     * getter for the averagePurchase
     * 
     * @return averagePurchase that tells how much the user spends on average on the e-store
     */
    public float getAveragePurchase(){return this.averagePurchaseAmt;}

    /**
     * getter for the purchasedCounts
     * 
     * @return purchaseCounts that tells how many of each product the user has purchased in account lifetime
     */
    public HashMap<Integer,Integer> getPurchasedCounts(){return this.purchasedCounts;}

    /**
     * getter for the typeCounts
     * 
     * @return typeCounts that tells how many products the user has purchased of the specific type in account lifetime
     */
    public HashMap<CardType,Integer> getTypeCounts(){return this.typeCounts;}

    /**
     * getter for typeRevenues
     * 
     * @return typeRevenues that tells how much money the user has spent on each card type in account lifetime
     */
    public HashMap<CardType,Float> getTypeRevenues(){return this.typeRevenues;}

    /**
     * getter for averageSessionTime
     * 
     * @return averageSessionTime that tells how much time the user spends on average when visiting the e-store
     */
    public Float getAverageSessionTime(){return this.averageSessionTime;}

    /**
     * getter for mostExpensiveOrder
     * 
     * @return mostExpensiveOrder that tells the most expensive order that the user has purchased in account lifetime
     */
    public ShoppingCart getMostExpensiveOrder(){return this.mostExpensiveOrder;}

    /**
     * getter for mostPurchased
     * 
     * @return mostPurchased that tells which product the user has purchased the most via the product id
     */
    public int getMostPurchased(){return this.mostPurchased;}

    /**
     * getter for lifetimeSessionTime
     * 
     * @return lifetimeSessionTime that tells how much time the user has spent in the e-store in account lifetime
     */
    public float getLifetimeSessionTime(){return this.lifetimeSessionTime;}

    /**
     * getter for mostPopularType
     * 
     * @return mostPopularType that tells which card type the user has purchased more of on the e-store in account lifetime
     */
    public CardType getMostPopularType(){return this.mostPopularType;}

    /**
     * setter for the id of the User statistic
     * 
     * @param id the id of the user who the statistics belongs
     */
    public void setId(int id) {this.id = id;}

    /**
     * setter for purchaseCounter
     * 
     * @param purchaseCounter the number of times the user has purchased from the e-store
     */
    public void setPurchaseCounter(int purchaseCounter){this.purchaseCounter = purchaseCounter;}

    /**
     * setter for lifetimeSpending
     * 
     * @param lifetimeSpending the lifetime amount that a user has spend on the e-store
     */
    public void setLifetimeSpending(float lifetimeSpending){this.lifetimeSpending = lifetimeSpending;}

    /**
     * setter for averagePurchase
     * 
     * @param averagePurchaseAmt the average amount that the user purchases on the e-store
     */
    public void setAveragePurchase(float averagePurchaseAmt){this.averagePurchaseAmt = averagePurchaseAmt;}

    /**
     * setter for purchasedCounts
     * 
     * @param purchasedCounts the dictionary for how many times the user has purchased each product in the e-store
     */
    public void setPurchasedCounts(HashMap<Integer,Integer> purchasedCounts){this.purchasedCounts = purchasedCounts;}

    /**
     * setter for typeCounts
     * 
     * @param typeCounts the dictionary for how many times the user has purchased cards of each type in the e-store
     */
    public void setTypeCounts(HashMap<CardType,Integer> typeCounts){this.typeCounts = typeCounts;}

    /**
     * setter for typeRevenues
     * 
     * @param typeRevenues the dictionary for the total amount of money spent by the user of each card type in the e-store
     */
    public void setTypeRevenues(HashMap<CardType,Float> typeRevenues){this.typeRevenues = typeRevenues;}

    /**
     * setter for averageSessionTime
     * 
     * @param averageSessionTime the average amount of time the user spends while on the e-store
     */
    public void setAverageSessionTime(float averageSessionTime){this.averageSessionTime = averageSessionTime;}

    /**
     * setter for mostExpensiveOrder
     * 
     * @param mostExpensiveOrder the most expensive order that the user has purchased, in the form of a shopping cart
     */
    public void setMostExpensiveOrder(ShoppingCart mostExpensiveOrder){this.mostExpensiveOrder = mostExpensiveOrder;}

    /**
     * setter for mostPurchased
     * 
     * @param productId the product Id of the card the user has purchased the most of
     */
    public void setMostPurchased(int productId){this.mostPurchased = productId;}

    /**
     * setter for lifetimeSessionTime
     * 
     * @param lifetimeSessionTime the total time the user has spent on the e-store
     */
    public void setLifetimeSessionTime(float lifetimeSessionTime){this.lifetimeSessionTime = lifetimeSessionTime;}

    /**
     * setter for mostPopularType
     * 
     * @param mostPopularType the type the user has purchased the most of
     */
    public void setMostPopularType(CardType mostPopularType){this.mostPopularType = mostPopularType;}

    /**
     * the method increments the purchase counter for the user
     */
    public void incrementPurchaseCounter(){
        this.purchaseCounter+= 1;
    }

    /**
     * the method adds to the total amount of money spent by the user
     * 
     * @param lifetimeIncrease the new money spent by the user
     */
    public void incrementLifetimeAmount(float lifetimeIncrease){
        this.lifetimeSpending += lifetimeIncrease;
    }

    /**
     * Adds to the total amount of time spent logged in
     * 
     * @param lifetimeIncrease the new time to add
     */
    public void incrementLifetimeSession(float lifetimeIncrease) {
        this.lifetimeSessionTime += lifetimeIncrease;
    }

    /**Adds amount to the value in the map pointed to by type
     * 
     * @param type directs to the value
     * @param amount the amount to be added
     */
    public void increaseTypeTally(CardType type, int amount){
        if (this.typeCounts.containsKey(type)) {
            this.typeCounts.put(type, this.typeCounts.get(type) + amount);
        } else {
            this.typeCounts.put(type, amount);
        }
    }

    /**Takes an array of products and adds the revenue generated to the typeRevenues map
     * 
     * @param purchasedProducts an array of the products purchased
     */
    public void increaseTypeRevenue(Product[] purchasedProducts){
        for(Product product: purchasedProducts) {
            CardType[] typeArray = product.getTypes();
            for(CardType type : typeArray) {
                if (this.typeRevenues.containsKey(type)) {
                    this.typeRevenues.put(type, this.typeRevenues.get(type) + (product.getPrice() * product.getQuantity()));
                } else {
                    this.typeRevenues.put(type, product.getPrice() * product.getQuantity());
                }
            }
        }
    }

    /**
     * this method increments the counts of the products the user has purchased in their account lifetime
     * 
     * @param products the products that the user has purchased in an array of products
     */
    public void increaseProductTally(Product[] products){
        for (Product product : products) {
            if (this.purchasedCounts.containsKey(product.getId())) {
                int currentCount = this.purchasedCounts.get(product.getId());
                currentCount += product.getQuantity();
                this.purchasedCounts.put(product.getId(), currentCount);
            } else {
                this.purchasedCounts.put(product.getId(), product.getQuantity());
            }
        }
    }

    /**
     * this method calculates the average purchase amount for the user by taking the lifetimeSpending divided by the number of lifetime purchases
     */
    public void calculateAveragePurchaseAmount(){
        this.averagePurchaseAmt = (float) this.lifetimeSpending / this.purchaseCounter;
    }

    /**
     * this method determines the most purchased product by going through the dictionary of purchasedCounts
     */
    public void determineMostPurchasedProduct(){
        int mostPurchasedIndex = -1;
        Integer[] keys = new Integer[this.purchasedCounts.size()];
        this.purchasedCounts.keySet().toArray(keys);
        Integer[] values = new Integer[this.purchasedCounts.size()];
        this.purchasedCounts.values().toArray(values);
        for (int i = 0; i < values.length; i++) {
            if (mostPurchasedIndex < 0) {
                mostPurchasedIndex = i;
            } else if(values[i] > values[i - 1]){
                mostPurchasedIndex = i;
            }
        }
        this.mostPurchased = keys[mostPurchasedIndex];
    }

    /**
     * this method compares the new order to the current most expensive order and replaces it if the new order cost more
     * 
     * @param cart the new order the user has made
     */
    public void determineMostExpensiveOrder(ShoppingCart cart){
        if (this.mostExpensiveOrder.GetTotalPrice() < cart.GetTotalPrice()) {
            this.mostExpensiveOrder = cart;
        }
    }

    /**
     * this method calculates the average session time by taking the total time the user has spent on the e-store divided by the number of times they purchased something
     */
    public void calculateAverageSessionTime(){
        incrementPurchaseCounter();
        this.averageSessionTime = (float) this.lifetimeSessionTime / this.purchaseCounter;
    }

    /**
     * this method determines the most popular card type for this user by going through the dictionary typeCounts
     */
    public void determineMostPopularType(){
        int count = 0;
        CardType max = null;
        for(CardType type : this.typeCounts.keySet()) {
            if(this.typeCounts.get(type) >= count) {
                count = this.typeCounts.get(type);
                max = type;
            }
        }
        this.mostPopularType = max;
    }

    /**
     * this method creates a string representation of a userStatistic object
     */
    @Override
    public String toString(){
        return String.format(STRING_FORMAT,id,purchaseCounter,lifetimeSpending,lifetimeSessionTime,
                             averagePurchaseAmt,purchasedCounts,mostPurchased,mostExpensiveOrder,typeCounts,
                             typeRevenues,mostPopularType,averageSessionTime);
    }

    /**
     * this method determines if two userStatistics are equal by comparing hashcodes
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof UserStatistic)) {
            return false;
        }
        UserStatistic otherStatistic = (UserStatistic) other;
        return (this.hashCode() == otherStatistic.hashCode());
    }

    /**
     * this method uses id for the hashcode
     */
    @Override
    public int hashCode(){
        return this.id; 
    }
}
