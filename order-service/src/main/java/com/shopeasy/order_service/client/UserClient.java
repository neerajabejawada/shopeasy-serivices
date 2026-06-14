package com.shopeasy.order_service.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserResponse getUserById(@PathVariable Long id);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserResponse {
        private Long id;
        private String name;
        private String email;
        private String phone;
    }
}