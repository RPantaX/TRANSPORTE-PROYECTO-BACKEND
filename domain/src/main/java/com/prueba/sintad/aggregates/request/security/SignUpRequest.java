package com.prueba.sintad.aggregates.request.security;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")
    private String username;
    @Size(min = 5, max = 50, message = "El teléfono debe tener entre 5 y 50 caracteres")
    @Pattern(regexp = "\\d+", message = "El teléfono solo puede contener numeros")
    private String phone;
    private boolean isAdmin = false;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "La contraseña debe contener al menos una mayúscula, un carácter especial y tener un mínimo de 8 dígitos."
    )
    @NotNull(message = "Este campo no puede ser nulo")
    private String password;
}
