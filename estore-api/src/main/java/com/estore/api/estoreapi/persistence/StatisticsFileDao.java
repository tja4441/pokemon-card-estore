package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.CardType;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.StoreStatistic;
import com.estore.api.estoreapi.model.UserStatistic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class StatisticsFileDao implements StatisticsDao{
    private Map<Integer,UserStatistic> usersStats;              // provides a local cache of the UserStatistics
    private StoreStatistic store;                               //local cache of Store Statistics
    private ObjectMapper objectMapper;          //Converts between Product objects and JSON text file formats
    private String userFilename;                    //Filename for UserStatistics
    private String storeFilename;                   //Filename for StoreStastics

    public StatisticsFileDao(@Value("${storeStatistics.file}") String storeFilename, @Value("${userStatistics.file}") String userFilename, ObjectMapper mapper) throws IOException{
        this.storeFilename = storeFilename;
        this.userFilename = userFilename;
        this.objectMapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();
        loadStoreStats();
    }

    /**
     * Loads {@linkplain UserStatistic stats} from the JSON file into the map
     * 
     * @return true if the file was read successfuly
     * 
     * @throws IOException when file cannot be accessed or read from
     * 
     * @author Jensen DeRosier
     */
    private boolean load() throws IOException {
        usersStats = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of carts
        UserStatistic[] userStatsArray = objectMapper.readValue(new File(userFilename),UserStatistic[].class);

        // Add each product to the tree map
        for (UserStatistic userStats : userStatsArray) {
            usersStats.put(userStats.getId(), userStats);
        }
        return true;
    }

    /**
     * Loads {@linkplain StoreStatistic storeStats} from the storeStats JSON file
     * 
     * @return true if the file was read successfuly
     * 
     * @throws IOException when file cannot be accessed or read from
     * 
     * @author Jensen DeRosier
     */
    private boolean loadStoreStats() throws IOException {
        store = objectMapper.readValue(new File(storeFilename),StoreStatistic.class);
        return true;
    }

    private boolean save() throws IOException {
        UserStatistic[] cartArray = getUserStats();
        StoreStatistic store = getStoreStatistic();

        // Serializes the Java Objects to JSON objects into the file
        objectMapper.writeValue(new File(userFilename),cartArray);
        objectMapper.writeValue(new File(storeFilename), store);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public StoreStatistic getStoreStatistic(){
        return store;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserStatistic createUserStats(int uID, String username) throws IOException{
        synchronized(usersStats) {
            UserStatistic u = new UserStatistic(uID, username);
            for(int id : usersStats.keySet()) {
                if(id == uID) {
                    return null;
                }
            }

            usersStats.put(uID, u);
            save();
            return u;
        }
    }

    /**
     * {@inheritDoc}
     */
    public UserStatistic[] getUserStats() {
        ArrayList<UserStatistic> userStatsList = new ArrayList<>();

        for (UserStatistic stats : usersStats.values()) {
            userStatsList.add(stats);
        }

        UserStatistic[] userStatsArray = new UserStatistic[userStatsList.size()];
        userStatsList.toArray(userStatsArray);
        return userStatsArray;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserStatistic updateUserStatistic(int id, ShoppingCart cart) throws IOException {
        UserStatistic stats = usersStats.get(id);
        stats.incrementLifetimeAmount(cart.GetTotalPrice());

        // turning the contents of the shopping cart into an array of products for the algorithms in the models to use
        ArrayList<Product> contentsList = new ArrayList<>();
        for (Product products : cart.getContents()) {
            contentsList.add(products);
        }
        Product[] contentsArray = new Product[contentsList.size()];
        contentsList.toArray(contentsArray);

        // using the contents array to calculate the tallies
        stats.increaseProductTally(contentsArray);
        stats.increaseTypeRevenue(contentsArray);

        //increasing the type tallies
        for (Product product : contentsArray) {
            for (CardType type : product.getTypes()) {
                stats.increaseTypeTally(type, product.getQuantity());
            }
        }
        stats.determineMostExpensiveOrder(cart);
        stats.calculateAveragePurchaseAmount();
        stats.determineMostPopularType();
        stats.determineMostPurchasedProduct();

        save();
        return stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserStatistic updateUserSessionData(int id, float sessionTime) throws IOException {
        UserStatistic stats = usersStats.get(id);

        stats.incrementLifetimeSession(sessionTime);
        stats.calculateAverageSessionTime();

        save();
        return stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreStatistic updateStoreStatistic(ShoppingCart cart) throws IOException {
        for(Product card : cart.getContents()) {
            for(CardType type : card.getTypes()) {
                store.increaseTypeRevenue(type, card.getQuantity() * card.getPrice());
            }
            store.addProductPurchaseAmounts(card.getId(), card.getQuantity());
        }
        store.calculateMostPopularProducts(usersStats);
        store.increaseTotalPurchase(cart.GetTotalPrice());
        store.checkCartAgainstMostExpensive(cart);
        store.calculateAveragePurchaseAmount();
        store.calculateMostPopularType(usersStats);

        save();
        return store;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StoreStatistic updateStoreSessionData(float sessionTime) throws IOException {
        store.increaseTotalSession(sessionTime);
        store.calculateAverageSessionTime();

        save();
        return store;
    }
}
