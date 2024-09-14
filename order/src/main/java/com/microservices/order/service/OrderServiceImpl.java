package com.microservices.order.service;

import com.microservices.order.entity.Order;
import com.microservices.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retrieves all orders.
     *
     * @return a list of all orders
     */
    @Override
    public List<Order>findAll() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves an order by its id.
     *
     * @param id the id of the order to retrieve
     * @return the order with the specified id
     * @throws NoSuchElementException if no order is found with the given id
     */
    @Override
    public Order findById(String id) {
        return orderRepository.findById(id)
                .orElseThrow( () -> new NoSuchElementException( "Not found order with id = " + id));
    }


    /**
     * Creates a new order.
     *
     * @param newOrder the order to create
     * @return the created order
     */
    @Override
    public Order create(Order newOrder) {
        return orderRepository.save(newOrder);
    }

    /**
     * Updates an existing order.
     *
     * @param id the ID of the order to update
     * @param newOrder the new details of the order
     * @return the updated order
     * @throws NoSuchElementException if no order is found with the given ID
     */
    @Override
    public Order update(String id, Order newOrder) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder == null) {
           throw new  NoSuchElementException( "Cannot found such order to update" );
        } else {
            existingOrder.setId(newOrder.getId());
            existingOrder.setPrice(newOrder.getPrice());
            existingOrder.setProductName(newOrder.getProductName());
            existingOrder.setUserId(newOrder.getUserId());
            return orderRepository.save(existingOrder);
        }
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @throws IllegalArgumentException if the ID is invalid
     * @throws RuntimeException if an unexpected error occurs during deletion
     */
    @Override
    public void delete(String id) {
        try {
            // Attempt to delete the order by its ID
            orderRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            // Handle case where the ID is invalid
            throw new IllegalArgumentException("Order id is invalid");
        } catch (Exception e) {
            // Handle other unforeseen exceptions
            throw new RuntimeException("An unexpected error occurred while deleting the order with ID");
        }
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

}
