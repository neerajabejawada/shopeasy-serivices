package com.shopeasy.user_service.util;

/*
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
*/
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    /*@Value("${jwt.secret:neeraja-secret-key-minimum-32-characters-long}")
    private String jwtSecret;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpiration;

    *//**
     * Generate JWT token
     *//*
    public String generateToken(Long userId, String username, String email) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        return JWT.create()
                .withSubject(username)
                .withClaim("userId", userId)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(algorithm);
    }

    *//**
     * Validate JWT token
     *//*
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWT.require(algorithm)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    *//**
     * Extract username from token
     *//*
    public String getUsernameFromToken(String token) {
        try {
            return JWT.decode(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    *//**
     * Extract userId from token
     *//*
    public Long getUserIdFromToken(String token) {
        try {
            return JWT.decode(token).getClaim("userId").asLong();
        } catch (Exception e) {
            return null;
        }
    }
*/}