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
    UserStatistic updateUserStatistic(int id, ShoppingCart cart) throws IOException;

    /**
     * Updates the Session Time Data for the user with the provided ID
     * 
     * @return 
     */
    UserStatistic updateUserSessionData(int id, float sessionTime) throws IOException;

    /**Updates Store Statistics with the data from the passed in cart
     * 
     * @param cart the cart that was just checked out
     * 
     * @return the updated Store Statistics
     * 
     * @throws IOException if error accessing data
     */
    StoreStatistic updateStoreStatistic(ShoppingCart cart) throws IOException;

    /**Updates Store Session Data with the new session time
     * 
     * @param sessionTime the total session time
     * 
     * @return the updated Store Statistics
     * 
     * @throws IOException if error accessing data
     */
    StoreStatistic updateStoreSessionData(float sessionTime) throws IOException;
}