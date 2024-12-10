package com.prueba.sintad.ports.out.security;

import com.prueba.sintad.aggregates.dto.security.UserDTO;
import com.prueba.sintad.aggregates.request.security.SignInRequest;
import com.prueba.sintad.aggregates.request.security.SignUpRequest;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.security.AuthenticationResponse;

public interface AuthenticationServiceOut {
    ResponseApi<UserDTO> signUpOut(SignUpRequest signUpRequest);
    AuthenticationResponse signinOut(SignInRequest signInRequest);
}
