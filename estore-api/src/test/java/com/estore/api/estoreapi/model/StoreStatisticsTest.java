package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class StoreStatisticsTest {
    @Test
    public void testCheckCartForMostExpensive() {
        //Setup
        ShoppingCart c0 = new ShoppingCart(0);
        ShoppingCart c1 = new ShoppingCart(1);
        ShoppingCart c2 = new ShoppingCart(2);
        ShoppingCart c3 = new ShoppingCart(3);
        ShoppingCart c4 = new ShoppingCart(4);
        ShoppingCart c5 = new ShoppingCart(5);

        c0.setTotalPrice(50);
        c1.setTotalPrice(25);
        c2.setTotalPrice(10);
        c3.setTotalPrice(5);
        c4.setTotalPrice(2);
        c5.setTotalPrice(100);

        ShoppingCart[] initialCarts = {c0, c1, c2, c3, c4};
        StoreStatistic storeStats = new StoreStatistic();
        storeStats.setMostExpensiveCarts(initialCarts);

        //invoke
        storeStats.checkCartAgainstMostExpensive(c5);
        ShoppingCart mostExpensive = storeStats.getMostExpensiveCarts()[0];

        //assert
        assertEquals(c5, mostExpensive);
    }
}
