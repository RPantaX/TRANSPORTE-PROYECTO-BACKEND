package com.prueba.sintad.adapters.security;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.constants.Paths;
import com.prueba.sintad.aggregates.dto.security.UserDTO;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotAcceptableException;
import com.prueba.sintad.aggregates.request.security.SignInRequest;
import com.prueba.sintad.aggregates.request.security.SignUpRequest;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.security.AuthenticationResponse;
import com.prueba.sintad.entity.security.Role;
import com.prueba.sintad.entity.security.UserEntity;
import com.prueba.sintad.mapper.UserMapper;
import com.prueba.sintad.ports.in.security.JWTServiceIn;
import com.prueba.sintad.ports.out.security.AuthenticationServiceOut;
import com.prueba.sintad.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationAdapter implements AuthenticationServiceOut {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTServiceIn jwtServiceIn;

    private final UserMapper userMapper;
    @Override
    public ResponseApi<UserDTO> signUpOut(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new SintadAppNotAcceptableException("El usuario ya existe");
        }
        Role role = signUpRequest.isAdmin()? Role.ADMIN : Role.USER;
        UserEntity user = UserEntity.builder()
                .username(signUpRequest.getUsername())
                .role(role)
                .password(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .modifiedByUser(Constants.USER_DEFAULT)
                .state(Constants.STATUS_ACTIVE)
                .createdAt(Constants.getTimestamp())
                .build();

        UserEntity userSaved = userRepository.save(user);
        UserDTO userDTO = userMapper.convertToDto(userSaved);
        String path = Paths.SIGNUP;
        ResponseApi<UserDTO> responseApi = createGenericResponseApi(userDTO,path, Constants.STATUS_OK);
        log.info("User saved: {}", userSaved);
        return responseApi;
    }

    @Override
    public UserDTO signUpAdminOut(SignUpRequest signUpRequest) {
        return null;
    }

    @Override
    public AuthenticationResponse signinOut(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword())); //METODO PARA AUTHENTICATION,
        var user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username no valido"));

        var jwt = jwtServiceIn.generateTokenIn(user);
        AuthenticationResponse authenticationResponse =  new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

    private <T> ResponseApi<T> createGenericResponseApi(T data, String path, String status) {
        return ResponseApi.<T>builder()
                .path(path)
                .message(Constants.MESSAGE_OK)
                .status(status)
                .timestamp(Constants.getTimestamp())
                .data(data)
                .build();
    }

}
