package com.shopeasy.order_service.service;

import com.shopeasy.order_service.client.ProductClient;
import com.shopeasy.order_service.client.UserClient;
import com.shopeasy.order_service.dto.OrderRequest;
import com.shopeasy.order_service.dto.OrderResponse;
import com.shopeasy.order_service.entity.Order;
import com.shopeasy.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;

    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "placeOrderFallback")
    public OrderResponse placeOrder(OrderRequest request) {
        UserClient.UserResponse user = userClient.getUserById(request.getUserId());
        ProductClient.ProductResponse product = productClient.getProductById(request.getProductId());

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + request.getProductId());
        }

        productClient.reduceStock(request.getProductId(), request.getQuantity());

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(product.getPrice() * request.getQuantity());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return mapToResponse(saved, user, product);
    }

    // Fallback method when circuit is OPEN
    public OrderResponse placeOrderFallback(OrderRequest request, Exception ex) {
        System.out.println("⚠️ Circuit Breaker OPEN! User Service unavailable!");

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(0.0);

        Order saved = orderRepository.save(order);

        return new OrderResponse(
                saved.getId(),
                saved.getUserId(),
                "N/A",
                saved.getProductId(),
                "N/A",
                saved.getQuantity(),
                0.0,
                "PENDING",
                saved.getOrderDate()
        );
    }

    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "getOrderByIdFallback")
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        UserClient.UserResponse user = userClient.getUserById(order.getUserId());
        ProductClient.ProductResponse product = productClient.getProductById(order.getProductId());

        return mapToResponse(order, user, product);
    }

    public OrderResponse getOrderByIdFallback(Long id, Exception ex) {
        System.out.println("⚠️ Circuit Breaker OPEN! Returning order without user/product details!");

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                "N/A",
                order.getProductId(),
                "N/A",
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getOrderDate()
        );
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    try {
                        UserClient.UserResponse user = userClient.getUserById(order.getUserId());
                        ProductClient.ProductResponse product = productClient.getProductById(order.getProductId());
                        return mapToResponse(order, user, product);
                    } catch (Exception e) {
                        return new OrderResponse(
                                order.getId(),
                                order.getUserId(),
                                "N/A",
                                order.getProductId(),
                                "N/A",
                                order.getQuantity(),
                                order.getTotalPrice(),
                                order.getStatus(),
                                order.getOrderDate()
                        );
                    }
                })
                .collect(Collectors.toList());
    }

    private OrderResponse mapToResponse(Order order, UserClient.UserResponse user, ProductClient.ProductResponse product) {
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                user.getName(),
                order.getProductId(),
                product.getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getOrderDate()
        );
    }
}