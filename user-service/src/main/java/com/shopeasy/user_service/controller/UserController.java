
package com.shopeasy.user_service.controller;

import com.shopeasy.user_service.client.ProductClient;
import com.shopeasy.user_service.dto.UserRequest;
import com.shopeasy.user_service.dto.UserResponse;
import com.shopeasy.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}
