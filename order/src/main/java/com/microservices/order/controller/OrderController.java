package com.microservices.order.controller;

import com.microservices.order.entity.Order;
import com.microservices.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public ResponseEntity findAll() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody Order newOrder) {
        return new ResponseEntity<>(orderService.create(newOrder), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable(value = "id") String id, @RequestBody Order newOrder) {
        return new ResponseEntity<>(orderService.update(id, newOrder), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getOrdersByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(orderService.getOrdersByUserId(userId), HttpStatus.OK);
    }

}
