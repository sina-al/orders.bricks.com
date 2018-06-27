package com.bricks.orders.domain;


import org.springframework.data.annotation.Id;

public class Order {

    @Id
    private String id;

    private long quantity;

    public Order() {

    }

    public Order(String  id, long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s[id=\"%s\", quantity=%d]", getClass().getName(), id, quantity);
    }
}