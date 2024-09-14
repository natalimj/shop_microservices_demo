package com.microservices.order.service;

import com.microservices.order.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<Order> findAll();
    Order findById(String id);
    Order create(Order newOrder);
    Order update(String id, Order newOrder);
    void delete(String id);
    List<Order> getOrdersByUserId(String userId);
}
