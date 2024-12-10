package com.prueba.sintad.ports.in.security;

import com.prueba.sintad.aggregates.dto.security.UserDTO;
import com.prueba.sintad.aggregates.request.security.SignInRequest;
import com.prueba.sintad.aggregates.request.security.SignUpRequest;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.security.AuthenticationResponse;

public interface AuthenticationServiceIn {
    ResponseApi<UserDTO> signUpIn(SignUpRequest signUpRequest);
    UserDTO signUpAdminIn(SignUpRequest signUpRequest);
    AuthenticationResponse signinIn(SignInRequest signInRequest);
}
