package com.signin.jwt.service;


import com.signin.jwt.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String generateToken(UserDetails userDetails);
    String extractUserName(String token);
    boolean isTokenValid(String token, UserDetails userDetails);

    String generateFreshToken(Map<String, Object> extractClaims, UserDetails userDetails);
}
