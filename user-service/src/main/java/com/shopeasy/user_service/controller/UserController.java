
package com.shopeasy.user_service.controller;

import com.shopeasy.user_service.client.ProductClient;
import com.shopeasy.user_service.dto.UserRequest;
import com.shopeasy.user_service.dto.UserResponse;
import com.shopeasy.user_service.service.UserService;
import com.shopeasy.user_service.util.JwtUtil;
import com.shopeasy.user_service.dto.LoginRequest;
import com.shopeasy.user_service.dto.LoginResponse;
import com.shopeasy.user_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductClient.ProductResponse>> getAllProducts(){
        List<ProductClient.ProductResponse> products = userService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Login endpoint - Generate JWT token
     */
    /*@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // For demo: Accept any username/password
        // In production: Query database and verify password hash

        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        // For demo, create dummy user
        Long userId = 999L;  // Dummy ID
        String username = request.getUsername();
        String email = username + "@gmail.com";

        // Generate JWT token
        String token = jwtUtil.generateToken(userId, username, email);

        // Return token to client
        LoginResponse response = new LoginResponse(token, userId, username, email);
        return ResponseEntity.ok(response);
    }*/
}
