package com.signin.jwt.service.impl;


import com.signin.jwt.entities.User;
import com.signin.jwt.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("USER");

        claims.put("role", role);
        System.out.println("JWT claims: " + claims);
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).
                signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public String generateFreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode("R6w3yPOBZip4VFe207ElU1XhojVFezuKPsi5FgM/YXQ=");
        return Keys.hmacShaKeyFor(key);
    }

    private <T> T extractClaim(String token, Function<Claims, T> classResolvers) {
        final Claims claims = extractAllClaims(token);
        return classResolvers.apply(claims);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        boolean condition = userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
        return condition;
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}


