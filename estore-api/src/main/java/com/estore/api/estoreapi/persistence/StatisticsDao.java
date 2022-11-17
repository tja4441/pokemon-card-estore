package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.ShoppingCart;
import com.estore.api.estoreapi.model.StoreStatistic;
import com.estore.api.estoreapi.model.UserStatistic;

public interface StatisticsDao {
   /** Gets All {@linkplain UserStatistics} from the DAO
     * 
     * @return a native array of UserStatistic objects
     * 
     * @throws IOException if there is some error reading the file
     */
    UserStatistic[] getUserStats();

    /** Gets the {@linkplain StoreStatistic} from the DAO
     * 
     * @return the StoreStatistic Object
     * 
     * @throws IOException if there is some error reading the file
     */
    StoreStatistic getStoreStatistic();

    /**Creates a new {@linkplain UserStatistic} for the given ID
     * 
     * @param id the id of the UserStatistic to be created
     * @param username the username of the user that the stats reflect
     * 
     * @return the UserStatistic that was created
     * 
     * @throws IOException if there is some error reading the file
     */
    UserStatistic createUserStats(int id, String username) throws IOException;

    /**
     * Updates the UserStatistic corresponding to the User ID provided
     * 
     * @return UserStatistic corresponding to the statistic updated
     * 
     * @throws IOException if error accessing underlying data
     */
    UserStatistic updateUserStatistic(int id, ShoppingCart cart, Float sessionTime) throws IOException;

    /**Updates Store Statistics with the data from the passed in UserStatistics
     * 
     * @param newStats the UserStatistics to be incorporated into the store statistics
     * 
     * @return the updated Store Statistics
     * 
     * @throws IOException if error accessing data
     */
    StoreStatistic updateStoreStatistic(UserStatistic newStats) throws IOException;
}