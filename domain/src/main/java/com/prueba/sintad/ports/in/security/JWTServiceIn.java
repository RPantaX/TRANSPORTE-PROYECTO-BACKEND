package com.prueba.sintad.ports.in.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTServiceIn {
    String generateTokenIn(UserDetails userDetails);
    boolean validateTokenIn(String token, UserDetails userDetails);
    String extractUserNameIn(String token);
}
