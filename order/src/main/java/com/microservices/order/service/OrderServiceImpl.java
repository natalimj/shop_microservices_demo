package com.microservices.order.service;

import com.microservices.order.entity.Order;
import com.microservices.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order>findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order create(Order newOrder) {
        return orderRepository.save(newOrder);
    }

    @Override
    public Order update(String id, Order newOrder) {
        Order order =orderRepository.findById(id).get();
        order.setId(newOrder.getId());
        order.setPrice(newOrder.getPrice());
        order.setUserId(newOrder.getUserId());
        orderRepository.save(order);
        return order;
    }

    @Override
    public boolean delete(String id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }


}
