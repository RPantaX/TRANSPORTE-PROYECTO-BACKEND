package com.prueba.sintad.aggregates.request.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @NotNull(message = "El usuario no puede ser nulo")
    @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacio")
    private String password;
}
