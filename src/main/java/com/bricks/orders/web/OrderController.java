package com.bricks.orders.web;

import com.bricks.orders.domain.Order;
import com.bricks.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private static URI orderLocation(String id) {
        return UriComponentsBuilder.newInstance()
                .path("/api/order/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    private static ResponseEntity<Order> orderNotFound(String id) {
        return ResponseEntity.notFound()
                .location(orderLocation(id))
                .build();
    }

    @PostMapping
    public ResponseEntity<String> createOrder(Order order) {
        return orderService.createOrderAndReturnReferenceOnSuccess(order)
                .map(id ->  ResponseEntity.created(orderLocation(id))
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(id))
                .orElse(ResponseEntity.badRequest()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(order.getId()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        return orderService.getOrder(id)
                .map(ResponseEntity::ok)
                .orElse(orderNotFound(id));
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Order>> listOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }
}
