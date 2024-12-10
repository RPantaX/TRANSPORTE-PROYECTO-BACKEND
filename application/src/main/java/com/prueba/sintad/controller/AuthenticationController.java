package com.prueba.sintad.controller;

import com.prueba.sintad.aggregates.dto.security.UserDTO;
import com.prueba.sintad.aggregates.request.security.SignInRequest;
import com.prueba.sintad.aggregates.request.security.SignUpRequest;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.security.AuthenticationResponse;

import com.prueba.sintad.ports.in.security.AuthenticationServiceIn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceIn authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseApi<UserDTO>> signUpUser(@Valid @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpIn(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signinIn(signInRequest));
    }
}
