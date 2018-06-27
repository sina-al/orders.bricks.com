package com.bricks.orders.service;

import com.bricks.orders.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {


    public Optional<String> createOrderAndReturnReferenceOnSuccess(Order order) {
        return null;
    }

    public Optional<Order> getOrder(String id) {
        return null;
    }

    public List<Order> getOrders() {
        return null;
    }

}