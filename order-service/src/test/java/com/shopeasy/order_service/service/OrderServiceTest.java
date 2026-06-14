package com.shopeasy.order_service.service;

import com.shopeasy.order_service.client.ProductClient;
import com.shopeasy.order_service.client.UserClient;
import com.shopeasy.order_service.dto.OrderRequest;
import com.shopeasy.order_service.dto.OrderResponse;
import com.shopeasy.order_service.entity.Order;
import com.shopeasy.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private OrderService orderService;

    private OrderRequest orderRequest;
    private Order savedOrder;

    @BeforeEach
    public void setUp() {
        orderRequest = new OrderRequest(1L, 1L, 2);
        savedOrder = new Order(1L, 1L, 1L, 2, 3000.0, "PLACED", LocalDateTime.now());
    }

    @Test
    public void testPlaceOrderSuccess() {
        // Create mock responses
        UserClient.UserResponse mockUser = new UserClient.UserResponse(1L, "Neeraja", "neeraja@gmail.com", "9999");
        ProductClient.ProductResponse mockProduct = new ProductClient.ProductResponse(1L, "Laptop", "MacBook", 1500.0, 10);

        // Setup mocks
        when(userClient.getUserById(anyLong())).thenReturn(mockUser);
        when(productClient.getProductById(anyLong())).thenReturn(mockProduct);
        when(orderRepository.save(any())).thenReturn(savedOrder);

        // Execute
        OrderResponse response = orderService.placeOrder(orderRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Neeraja", response.getUserName());
    }

    @Test
    public void testInsufficientStock() {
        UserClient.UserResponse mockUser = new UserClient.UserResponse(1L, "Neeraja", "neeraja@gmail.com", "9999");
        ProductClient.ProductResponse mockProduct = new ProductClient.ProductResponse(1L, "Laptop", "MacBook", 1500.0, 1);

        when(userClient.getUserById(anyLong())).thenReturn(mockUser);
        when(productClient.getProductById(anyLong())).thenReturn(mockProduct);

        // Should throw exception
        assertThrows(RuntimeException.class, () -> orderService.placeOrder(orderRequest));
    }

    @Test
    public void testGetOrderById() {
        UserClient.UserResponse mockUser = new UserClient.UserResponse(1L, "Neeraja", "neeraja@gmail.com", "9999");
        ProductClient.ProductResponse mockProduct = new ProductClient.ProductResponse(1L, "Laptop", "MacBook", 1500.0, 10);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(savedOrder));
        when(userClient.getUserById(anyLong())).thenReturn(mockUser);
        when(productClient.getProductById(anyLong())).thenReturn(mockProduct);

        OrderResponse response = orderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }
}