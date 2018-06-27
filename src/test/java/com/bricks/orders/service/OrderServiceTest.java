package com.bricks.orders.service;

import com.bricks.orders.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OrderRepository.class, OrderService.class})
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderSuccess() {
        String id = "507f1f77bcf86cd799439011";

        Order order = mock(Order.class);
        when(order.getId()).thenReturn(id);

        given(orderRepository.existsById(id)).willReturn(false);
        given(orderRepository.save(order)).willReturn(order);

        Optional<String> reference = orderService.createOrderAndReturnReferenceOnSuccess(order);

        verify(orderRepository, times(1)).existsById(id);
        assertTrue(reference.isPresent());
        assertEquals(reference.get(), id);
    }

    @Test
    public void createOrderFailed() {
        String id = "507f1f77bcf86cd799439011";

        Order order = mock(Order.class);
        when(order.getId()).thenReturn(id);

        given(orderRepository.existsById(id)).willReturn(true);

        Optional<String> reference = orderService.createOrderAndReturnReferenceOnSuccess(order);

        verify(orderRepository, times(1)).existsById(id);
        assertFalse(reference.isPresent());
    }

    @Test
    public void createOrderNullReference() {

        Order order = mock(Order.class);
        given(order.getId()).willReturn(null);

        String generatedId = "507f1f77bcf86cd799439011";
        Order savedOrder = mock(Order.class);
        given(savedOrder.getId()).willReturn(generatedId);

        given(orderRepository.save(order)).willReturn(savedOrder);

        Optional<String> reference = orderService.createOrderAndReturnReferenceOnSuccess(order);

        assertTrue(reference.isPresent());
    }

    @Test
    public void getOrder() throws Exception {
        String validId = "507f1f77bcf86cd799439011";
        Optional<Order> optionalOrder = Optional.of(new Order());

        given(orderRepository.findById(validId)).willReturn(optionalOrder);
        assertSame(orderService.getOrder(validId), optionalOrder);
    }

    @Test
    public void getOrders() throws Exception {
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderRepository.findAll()).thenReturn(orders);
        assertEquals(orderService.getOrders(), orders);
    }
}
