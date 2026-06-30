package com.shopeasy.api_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class GlobalGatewayFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestId = UUID.randomUUID().toString();

        log.info("=== INCOMING REQUEST ===");
        log.info("Request ID: {}", requestId);
        log.info("Method: {}", exchange.getRequest().getMethod());
        log.info("Path: {}", exchange.getRequest().getPath());

        exchange.getResponse().getHeaders().add("X-Request-ID", requestId);

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;
            log.info("=== OUTGOING RESPONSE ===");
            log.info("Request ID: {}", requestId);
            log.info("Status: {}", exchange.getResponse().getStatusCode());
            log.info("Duration: {}ms", duration);
            log.info("===============================");
        }));
    }
}