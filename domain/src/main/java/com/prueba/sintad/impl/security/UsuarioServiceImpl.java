package com.prueba.sintad.impl.security;

import com.prueba.sintad.ports.in.security.UsuarioServiceIn;
import com.prueba.sintad.ports.out.security.UsuarioServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioServiceIn {

    private final UsuarioServiceOut usuarioServiceOut;

    @Override
    public UserDetailsService userDetailsServiceIn() {
        return usuarioServiceOut.userDetailsServiceOut();
    }
}
