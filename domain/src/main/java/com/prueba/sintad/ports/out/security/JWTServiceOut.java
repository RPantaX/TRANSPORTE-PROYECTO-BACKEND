package com.prueba.sintad.ports.out.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTServiceOut {
    String generateTokenOut(UserDetails userDetails);
    boolean validateTokenOut(String token, UserDetails userDetails);
    String extractUserNameOut(String token) ;
}
