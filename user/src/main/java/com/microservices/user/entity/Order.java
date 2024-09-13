package com.microservices.user.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orders")
public class Order {
    private String id;
    private String userId;
    private String productName;
    private Double price;
}
