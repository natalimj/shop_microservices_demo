package com.microservices.order.controller;


import com.microservices.order.entity.Order;
import com.microservices.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        // Arrange
        Order order = new Order();
        order.setId("1"); // Example field; set appropriate fields here
        order.setProductName("Chair"); // Initialize with actual fields if needed

      //  when(orderService.findAll()).thenReturn(Collections.singletonList(order));

        // Act & Assert
        given(orderService.findAll()).willReturn(Collections.singletonList(order));
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(order.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value(order.getProductName()));
    }
    @Test
    public void testFindById() throws Exception {
        // Arrange
        String id = "1";
        Order order = new Order(); // Initialize with appropriate values if needed
        order.setId(id);
        order.setUserId("4526357384");
        when(orderService.findById(id)).thenReturn(order);

        // Act & Assert
        mockMvc.perform(get("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(order.getUserId()));

    }

    @Test
    public void testCreate() throws Exception {
        // Arrange
        Order newOrder = new Order(); // Initialize with appropriate values if needed
        newOrder.setId("1");
        newOrder.setUserId("345657");
        newOrder.setProductName("Table");
        newOrder.setPrice(2000.00);
        when(orderService.create(newOrder)).thenReturn(newOrder);

        // Act & Assert
        mockMvc.perform(post("/orders")
                        .contentType("application/json")
                        .content("{\"id\":\"1\", \"userId\":\"345657\", \"productName\":\"Table\", \"price\":2000.00}")) //  JSON representation of the Order object
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newOrder.getId()))
                .andExpect(jsonPath("$.userId").value(newOrder.getUserId()))
                .andExpect(jsonPath("$.productName").value(newOrder.getProductName()))
                .andExpect(jsonPath("$.price").value(newOrder.getPrice()));
    }

    @Test
    public void testUpdate() throws Exception {
        // Arrange
        String id = "1";
        Order updatedOrder = new Order();
        updatedOrder.setId(id);
        updatedOrder.setUserId("345657");
        updatedOrder.setProductName("Chair");
        updatedOrder.setPrice(200.00);
        when(orderService.update(id, updatedOrder)).thenReturn(updatedOrder);

        // Act & Assert
        mockMvc.perform(put("/orders/{id}", id)
                        .contentType("application/json")
                        .content("{\"id\":\"1\", \"userId\":\"345657\", \"productName\":\"Chair\", \"price\":200.00}")) // JSON content with all fields
                .andExpect(status().isOk()) // Expect 200 OK status
                // Check each field in the response using jsonPath
                .andExpect(jsonPath("$.id").value(updatedOrder.getId()))
                .andExpect(jsonPath("$.userId").value(updatedOrder.getUserId()))
                .andExpect(jsonPath("$.productName").value(updatedOrder.getProductName()))
                .andExpect(jsonPath("$.price").value(updatedOrder.getPrice()));
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        // Arrange
        String id = "1";

        // Mock the repository behavior (no exception thrown for successful deletion)
        doNothing().when(orderService).delete(id);

        // Act & Assert
        mockMvc.perform(delete("/orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deleted order successfully"));
    }

    @Test
    public void testGetOrdersByUserId() throws Exception {
        // Arrange
        String userId = "user1";

        // Create two order objects with the same userId
        Order order1 = new Order();
        order1.setId("1");
        order1.setUserId(userId);
        order1.setProductName("Table");
        order1.setPrice(150.00);

        Order order2 = new Order();
        order2.setId("2");
        order2.setUserId(userId);
        order2.setProductName("Chair");
        order2.setPrice(75.00);

        // Mock the service call to return a list containing both orders
        when(orderService.getOrdersByUserId(userId)).thenReturn(Arrays.asList(order1, order2));

        // Act & Assert
        mockMvc.perform(get("/orders/user/{userId}", userId)
                        .contentType("application/json")) // Ensure correct content type
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2)) // Validate the size of the list
                // Validate first order's fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(order1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(order1.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value(order1.getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(order1.getPrice()))
                // Validate second order's fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(order2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value(order2.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName").value(order2.getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(order2.getPrice()));
    }
}
