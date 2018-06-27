package com.bricks.orders.web;

import com.bricks.orders.domain.Order;
import com.bricks.orders.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OrderService.class, OrderController.class})
public class OrderControllerUTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private OrderController orderController;

    private static URI orderLocation(String id) {
        return UriComponentsBuilder.newInstance()
                .path("/api/order/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @Test
    public void createOrderSucceeds() throws Exception {
        Order order = mock(Order.class);
        String reference = "507f1f77bcf86cd799439011";

        given(orderService.createOrderAndReturnReferenceOnSuccess(order)).willReturn(Optional.of(reference));

        assertEquals(
                orderController.createOrder(order),
                ResponseEntity.created(orderLocation(reference))
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(reference));
    }

    @Test
    public void createOrderFails() throws Exception {
        String reference = "507f1f77bcf86cd799439011";
        Order order = mock(Order.class);
        when(order.getId()).thenReturn(reference);

        given(orderService.createOrderAndReturnReferenceOnSuccess(order)).willReturn(Optional.empty());

        assertEquals(
                orderController.createOrder(order),
                ResponseEntity.badRequest()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(reference));
    }

    @Test
    public void getOrderSucceeds() throws Exception {
        Order existingOrder = mock(Order.class);
        String reference = "507f1f77bcf86cd799439011";

        given(orderService.getOrder(reference)).willReturn(Optional.of(existingOrder));

        assertEquals(orderController.getOrder(reference), ResponseEntity.ok(existingOrder));
    }

    @Test
    public void getOrderFails() throws Exception {
        String invalidReference = "507f1f77bcf86cd799439011";

        given(orderService.getOrder(invalidReference)).willReturn(Optional.empty());

        assertEquals(
                orderController.getOrder(invalidReference),
                ResponseEntity.notFound()
                        .location(orderLocation(invalidReference))
                        .build());
    }
}
