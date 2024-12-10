package com.prueba.sintad.adapters.security;

import com.prueba.sintad.ports.out.security.JWTServiceOut;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JWTAdapter implements JWTServiceOut {

    @Value("${jwt.secret}")
    private String secretKeyPath;

    @Override
    public String generateTokenOut(UserDetails userDetails) {
        boolean isAdmin = userDetails.getAuthorities().equals("ADMIN");
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // Establece la fecha y hora en que el token fue emitido (claim "iat").
                .setExpiration(new Date(System.currentTimeMillis() + 120000)) //expira en 2 minutos desde el tiempo de ejecucion
                .signWith(getSignKey(), SignatureAlgorithm.HS256)  //Define el algoritmo de firma y la clave para firmar el token
                .claim("roles", userDetails.getAuthorities())
                .claim("isAdmin", isAdmin)
                .compact();
    }

    @Override
    public boolean validateTokenOut(String token, UserDetails userDetails) {
        final String username = extractUserNameOut(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String extractUserNameOut(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode(secretKeyPath);
        return Keys.hmacShaKeyFor(key);
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
