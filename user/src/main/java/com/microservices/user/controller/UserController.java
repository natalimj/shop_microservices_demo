package com.microservices.user.controller;

import com.microservices.user.entity.User;
import com.microservices.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>>  findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User newUser) {
        return new ResponseEntity<>(userService.create(newUser), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable(value = "id") String id, @RequestBody User newUser) {
        return new ResponseEntity<>(userService.update(id, newUser), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order/{userId}")
    public ResponseEntity getOrdersByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getOrdersByUserId(userId), HttpStatus.OK);
    }
}
