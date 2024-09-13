package com.microservices.user.service;

import com.microservices.user.entity.Order;
import com.microservices.user.entity.User;
import com.microservices.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
       // Optional<User> user = userRepository.findById(id);
       // return user.orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return userRepository.findById(id);
    }

    @Override
    public User create(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User update(String id, User updatedUser) {
        User user = userRepository.findById(id).get();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    @Override
    public boolean delete(String id) {
        userRepository.deleteById(id);
        return true;
    }

    public List<Order> getOrdersByUserId(String userId){
        String url = "http://localhost:8002/api/v1/orders/user/" + userId;
        List<Order>  orders = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {}).getBody();
        return orders;
    }
}
