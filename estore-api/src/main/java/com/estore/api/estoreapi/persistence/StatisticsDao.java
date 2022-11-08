package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.HashMap;

import com.estore.api.estoreapi.model.CardType;
import com.estore.api.estoreapi.model.Product;
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

    /**
     * Updates the UserStatistic corresponding to the User ID provided
     * 
     * @return UserStatistic corresponding to the statistic updated
     * 
     * @throws IOException if error accessing underlying data
     */
    UserStatistic updateUserStatistic(int id, ShoppingCart cart, Float sessionTime) throws IOException;
}