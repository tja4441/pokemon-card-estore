package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class OrderHistoryTest { 

    @Test
    public void testGetters() {
        OrderHistory order = new OrderHistory(1, new ShoppingCart(1), 1, "11/1/2022 09:03:50");

        assertEquals(1, order.getId());
        assertEquals(new ShoppingCart(1), order.getPurchasedCart());
        assertEquals(1, order.getOrderNumber());
        assertEquals("11/1/2022 09:03:50", order.getTimeStamp());
    }

    @Test
    public void testToString() {
        OrderHistory order = new OrderHistory(1, new ShoppingCart(1), 1, "11/1/2022 09:03:50");

        assertEquals("OrderHistory [id=1, purchasedCart=ShoppingCart [id=1, contents=[], totalPrice=0.000000], orderNumber=1, timeStamp=11/1/2022 09:03:50]",
         order.toString());
    }
    
}
