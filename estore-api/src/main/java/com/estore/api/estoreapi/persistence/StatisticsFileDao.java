package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;

import com.estore.api.estoreapi.model.StoreStatistic;
import com.estore.api.estoreapi.model.UserStatistic;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StatisticsFileDao {
    private Map<Integer,UserStatistic> usersStats;            // provides a local cache of the UserStatistics
    private StoreStatistic store;               //local 
    private ObjectMapper objectMapper;          //Converts between Product objects and JSON text file formats
    private String userFilename;                    //Filename for UserStatistics
    private String storeFilename;                   //Filename for StoreStastics

    public StatisticsFileDao(@Value("${storeStatistics.file}") String storeFilename, @Value("${userStatistics.file}") String userFilename, ObjectMapper mapper) throws IOException{
        this.storeFilename = storeFilename;
        this.userFilename = userFilename;
        this.objectMapper = mapper;
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

    public StoreStatistic getStore() {
        return store;
    }

    public UserStatistic[] getAllStats() {
        ArrayList<UserStatistic> userStatsList = new ArrayList<>();

        for (UserStatistic stats : usersStats.values()) {
            userStatsList.add(stats);
        }

        UserStatistic[] userStatsArray = new UserStatistic[userStatsList.size()];
        userStatsList.toArray(userStatsArray);
        return userStatsArray;
    }
}
