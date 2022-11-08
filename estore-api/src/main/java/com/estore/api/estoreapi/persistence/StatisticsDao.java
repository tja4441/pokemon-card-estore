package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.StoreStatistic;
import com.estore.api.estoreapi.model.UserStatistic;

public interface StatisticsDao {
   /** Gets All {@linkplain UserStatistics} from the DAO
     * 
     * @return a native array of UserStatistic objects
     * 
     * @throws IOException if there is some error reading the file
     */
    UserStatistic[] getAllUserStats();

    /** Gets the {@linkplain StoreStatistic} from the DAO
     * 
     * @return the StoreStatistic Object
     * 
     * @throws IOException if there is some error reading the file
     */
    StoreStatistic getStoreStatistic();
}
