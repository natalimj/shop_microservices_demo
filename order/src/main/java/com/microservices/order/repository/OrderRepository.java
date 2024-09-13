package com.microservices.order.repository;

import com.microservices.order.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findByUserId(String userId);
}
