package com.prueba.sintad.impl.security;

import com.prueba.sintad.aggregates.dto.security.UserDTO;
import com.prueba.sintad.aggregates.request.security.SignInRequest;
import com.prueba.sintad.aggregates.request.security.SignUpRequest;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.security.AuthenticationResponse;
import com.prueba.sintad.ports.in.security.AuthenticationServiceIn;
import com.prueba.sintad.ports.out.security.AuthenticationServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationServiceIn {

    private final AuthenticationServiceOut authenticationServiceOut;

    @Override
    public ResponseApi<UserDTO> signUpIn(SignUpRequest signUpRequest) {
        return authenticationServiceOut.signUpOut(signUpRequest);
    }

    @Override
    public UserDTO signUpAdminIn(SignUpRequest signUpRequest) {
        return authenticationServiceOut.signUpAdminOut(signUpRequest);
    }

    @Override
    public AuthenticationResponse signinIn(SignInRequest signInRequest) {
        return authenticationServiceOut.signinOut(signInRequest);
    }
}
