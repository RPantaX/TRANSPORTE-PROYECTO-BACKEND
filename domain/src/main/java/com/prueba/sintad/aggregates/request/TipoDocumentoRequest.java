package com.prueba.sintad.aggregates.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TipoDocumentoRequest {
    @Size(max = 20, message = "El código debe tener como máximo 20 caracteres")
    @Pattern(regexp = "\\d+", message = "El número de documento solo puede contener dígitos")
    @NotBlank(message = "El código no puede estar vacío")
    private String codigo;

    @Size(max = 100, message = "El nombre debe tener como máximo 100 caracteres")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Size(max = 200, message = "La descripción debe tener como máximo 200 caracteres")
    private String descripcion;
}
