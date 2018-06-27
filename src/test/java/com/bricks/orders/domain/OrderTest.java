package com.bricks.orders.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class OrderTest {

    private static final String ID = "507f1f77bcf86cd799439011";
    private static final long QUANTITY = 10;

    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    public void getAndSetReferenceConsistent() throws Exception {
        assertNull(order.getId());
        order.setId(ID);
        assertEquals(ID, order.getId());
    }

    @Test
    public void getAndSetQuantityConsistent() throws Exception {
        assertEquals(0, order.getQuantity());
        order.setQuantity(QUANTITY);
        assertEquals(QUANTITY, order.getQuantity());
    }

    @Test
    public void testString() throws Exception {
        order.setId(ID);
        order.setQuantity(QUANTITY);
        String expected = String.format("%s[id=\"%s\", quantity=%d]", order.getClass().getName(), ID, QUANTITY);
        String actual = order.toString();
        assertEquals(expected, actual);
    }

}
