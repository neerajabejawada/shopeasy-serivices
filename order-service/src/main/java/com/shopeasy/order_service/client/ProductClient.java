package com.shopeasy.order_service.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductResponse getProductById(@PathVariable Long id);

    @PutMapping("/api/products/{id}/reduce-stock")
    void reduceStock(@PathVariable Long id, @RequestParam Integer quantity);

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

