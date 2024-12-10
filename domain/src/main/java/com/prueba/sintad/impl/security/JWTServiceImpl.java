package com.prueba.sintad.impl.security;

import com.prueba.sintad.ports.in.security.JWTServiceIn;
import com.prueba.sintad.ports.out.security.JWTServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTServiceIn {

    private final JWTServiceOut jwtServiceOut;

    @Override
    public String generateTokenIn(UserDetails userDetails) {
        return jwtServiceOut.generateTokenOut(userDetails);
    }

    @Override
    public boolean validateTokenIn(String token, UserDetails userDetails) {
        return jwtServiceOut.validateTokenOut(token, userDetails);
    }

    @Override
    public String extractUserNameIn(String token) {
        return jwtServiceOut.extractUserNameOut(token);
    }
}
