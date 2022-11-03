package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public void testSetters() {
        OrderHistory order = new OrderHistory(0, null, 0, null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        order.setId(2);
        order.setPurchasedCart(new ShoppingCart(2));
        order.setOrderNumber(2);
        order.setTimeStamp(LocalDateTime.now().format(dtf));

        assertEquals(2, order.getId());
        assertEquals(new ShoppingCart(2), order.getPurchasedCart());
        assertEquals(2, order.getOrderNumber());
        assertEquals(LocalDateTime.now().format(dtf), order.getTimeStamp());
    }

    @Test
    public void testToString() {
        OrderHistory order = new OrderHistory(1, new ShoppingCart(1), 1, "11/1/2022 09:03:50");

        assertEquals("OrderHistory [orderNumber=1, timeStamp=11/1/2022 09:03:50, purchasedCart=ShoppingCart [id=1, contents=[], totalPrice=0.000000], id=1]",
         order.toString());
    }

    @Test
    public void testEquals() {
        OrderHistory o1 = new OrderHistory(1, new ShoppingCart(1), 1, "11/1/2022 09:03:50");
        OrderHistory o2 = new OrderHistory(10, new ShoppingCart(10), 1, "01/1/1822 00:03:50");
        OrderHistory o3 = new OrderHistory(1, new ShoppingCart(1), 2, "11/1/2022 09:03:50");
        ShoppingCart s = new ShoppingCart(0);

        assertEquals(o1, o2);
        assertNotEquals(o1, o3);
        assertNotEquals(o2, o3);
        assertNotEquals(o1, s);
    }

    @Test
    public void testHashCode() {
        OrderHistory orderHistory = new OrderHistory(1, new ShoppingCart(1), 1, "11/1/2022 09:03:50");

        assertEquals(orderHistory.getOrderNumber(), orderHistory.hashCode());
    }
    
}
