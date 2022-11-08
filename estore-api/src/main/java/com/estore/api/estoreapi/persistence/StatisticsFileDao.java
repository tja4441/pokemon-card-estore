package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.StoreStatistic;
import com.estore.api.estoreapi.model.UserStatistic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class StatisticsFileDao implements StatisticsDao{
    private Map<Integer,UserStatistic> usersStats;            // provides a local cache of the UserStatistics
    private StoreStatistic store;               //local 
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

    public StoreStatistic getStoreStatistic(){
        return store;
    }

    public UserStatistic[] getUserStats() {
        ArrayList<UserStatistic> userStatsList = new ArrayList<>();

        for (UserStatistic stats : usersStats.values()) {
            userStatsList.add(stats);
        }

        UserStatistic[] userStatsArray = new UserStatistic[userStatsList.size()];
        userStatsList.toArray(userStatsArray);
        return userStatsArray;
    }

    @Override
    public UserStatistic updateUserStatistic(int id, ShoppingCart cart, Float sessionTime) throws IOException {
        UserStatistic stats = usersStats.get(id);
        stats.incrementLoginCounter();
        stats.incrementPurchaseCounter();
        stats.incrementLifetimeAmount(cart.GetTotalPrice());
        stats.incrementLifetimeSession(sessionTime);
        stats.increaseProductTally(cart.getContents().toArray(new Product[0]));
        stats.increaseTypeRevenue(cart.getContents().toArray(new Product[0]));
        stats.determineMostExpensiveOrder(cart);
        stats.calculateAveragePurchaseAmount();
        stats.calculateAverageSessionTime();
        stats.determineMostPopularType();

        return stats;
    }
}
