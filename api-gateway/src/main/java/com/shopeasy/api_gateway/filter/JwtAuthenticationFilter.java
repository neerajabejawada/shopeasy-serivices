package com.shopeasy.api_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/*

@Component
@Slf4j
public class JwtAuthenticationFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().toString();

        // Skip authentication for login endpoint
        if (path.contains("/login") || path.contains("/eureka")) {
            return chain.filter(exchange);
        }

        // Get Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header for path: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extract token
        String token = authHeader.substring(7);

        log.info("JWT Token received for path: {}", path);
        log.info("Token: {}", token.substring(0, Math.min(20, token.length())) + "...");

        // For now, just forward (we'll add validation later)
        return chain.filter(exchange);
    }
}*/
