package com.microservices.order.service;


import com.microservices.order.entity.Order;
import com.microservices.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Order> orders = Arrays.asList(
                new Order("1", "user1", "product1", 100.0),
                new Order("2", "user2", "product2", 200.0)
        );
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.findAll();
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("product1", result.get(0).getProductName());
    }

    @Test
    public void testFindById_Success() {
        Order order = new Order("1", "user1", "product1", 100.0);
        when(orderRepository.findById("1")).thenReturn(Optional.of(order));

        Order result = orderService.findById("1");
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("product1", result.getProductName());
    }

    @Test
    public void testFindById_NotFound() {
        when(orderRepository.findById("1")).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            orderService.findById("1");
        });
        assertEquals("Not found order with id = 1", thrown.getMessage());
    }

    @Test
    public void testCreate() {
        Order newOrder = new Order("1", "user1", "product1", 100.0);
        when(orderRepository.save(newOrder)).thenReturn(newOrder);

        Order result = orderService.create(newOrder);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("product1", result.getProductName());
    }

    @Test
    public void testUpdate_Success() {
        Order existingOrder = new Order("1", "user1", "product1", 100.0);
        Order updatedOrder = new Order("1", "user1", "productUpdated", 150.0);

        when(orderRepository.findById("1")).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        Order result = orderService.update("1", updatedOrder);
        assertNotNull(result);
        assertEquals("productUpdated", result.getProductName());
        assertEquals(150.0, result.getPrice());
    }

    @Test
    public void testUpdate_NotFound() {
        Order updatedOrder = new Order("1", "user1", "productUpdated", 150.0);

        when(orderRepository.findById("1")).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            orderService.update("1", updatedOrder);
        });
        assertEquals("Cannot found such order to update", thrown.getMessage());
    }


    @Test
    public void testDelete_Success() {
        // Arrange: Mock the repository to do nothing when deleteById is called
        doNothing().when(orderRepository).deleteById("1");

        // Act & Assert: Execute method and verify that no exception is thrown
        assertDoesNotThrow(() -> orderService.delete("1"));
    }

    @Test
    public void testDelete_InvalidId() {
        doThrow(new IllegalArgumentException()).when(orderRepository).deleteById("invalidId454545");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            orderService.delete("invalidId454545");
        });
        assertEquals("Order id is invalid", thrown.getMessage());
    }

    @Test
    public void testDelete_Exception() {
        doThrow(new RuntimeException()).when(orderRepository).deleteById("1");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            orderService.delete("1");
        });
        assertEquals("An unexpected error occurred while deleting the order with ID", thrown.getMessage());
    }

    @Test
    public void testGetOrdersByUserId() {
        List<Order> orders = Arrays.asList(
                new Order("1", "user1", "product1", 100.0),
                new Order("2", "user1", "product2", 200.0)
        );
        when(orderRepository.findByUserId("user1")).thenReturn(orders);

        List<Order> result = orderService.getOrdersByUserId("user1");
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUserId());
        assertEquals("product1", result.get(0).getProductName());
    }
}