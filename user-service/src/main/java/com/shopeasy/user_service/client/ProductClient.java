package com.shopeasy.user_service.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products")
    List<ProductResponse> getAllProducts();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ProductResponse {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private Integer stock;
    }
}