package com.shopeasy.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.shopeasy.order_service")
@EnableJpaRepositories(basePackages = "com.shopeasy.order_service.repository")
@EntityScan(basePackages = "com.shopeasy.order_service.entity")
public class OrderServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
