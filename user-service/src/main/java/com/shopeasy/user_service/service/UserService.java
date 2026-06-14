
package com.shopeasy.user_service.service;

import com.shopeasy.user_service.client.ProductClient;
import com.shopeasy.user_service.dto.UserRequest;
import com.shopeasy.user_service.dto.UserResponse;
import com.shopeasy.user_service.entity.User;
import com.shopeasy.user_service.repository.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductClient productClient;

    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }

    // Add this to constructor (already in @RequiredArgsConstructor)

    @CircuitBreaker(name = "productServiceCB", fallbackMethod = "getProductsFallback")
    public List<ProductClient.ProductResponse> getAllProducts() {
        System.out.println("📦 Calling Product Service from User Service!");
        return productClient.getAllProducts();
    }

    public List<ProductClient.ProductResponse> getProductsFallback(Exception ex) {
        System.out.println("⚠️ Circuit Breaker OPEN! Product Service unavailable!");
        return List.of(); // Return empty list
    }
}

