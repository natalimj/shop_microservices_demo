package com.microservices.user.service;

import com.microservices.user.entity.Order;
import com.microservices.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();
    Optional<User> findById(String id);
    User create(User newUser);
    User update(String id, User newUser);
    boolean delete(String id);
    List<Order> getOrdersByUserId(String userId);
}
