package com.prueba.sintad.adapters.security;

import com.prueba.sintad.ports.out.security.UsuarioServiceOut;
import com.prueba.sintad.repository.security.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioAdapter implements UsuarioServiceOut {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceOut() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).orElseThrow( ()-> {
                    log.error("Usuario no encontrado");
                    return new UsernameNotFoundException("Usuario no encontrado");
                });
            }
        };
    }
}
