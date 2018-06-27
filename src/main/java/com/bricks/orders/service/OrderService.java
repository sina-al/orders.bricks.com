package com.bricks.orders.service;

import com.bricks.orders.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<String> createOrderAndReturnReferenceOnSuccess(Order order) {
        if (Objects.nonNull(order.getId())) {
            if (orderRepository.existsById(order.getId())) {
                return Optional.empty();
            }
        }
        return Optional.of(orderRepository.save(order).getId());
    }

    public Optional<Order> getOrder(String id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }
}