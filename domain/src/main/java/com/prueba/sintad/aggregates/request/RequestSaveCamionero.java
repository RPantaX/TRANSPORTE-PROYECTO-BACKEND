package com.prueba.sintad.aggregates.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RequestSaveCamionero {
    private Integer id;
    @Size(max = 8, min = 8, message = "El código debe tener exactamente 8 digitos")
    @Pattern(regexp = "\\d+", message = "El número de documento solo puede contener numeros")
    @NotBlank(message = "El numero de dni no puede estar vacío")
    private String dni;
    @Size(max = 250, message = "El nombre debe tener como máximo 100 caracteres")
    private String direccion;
    @Size(max = 9, min = 9, message = "El telefono debe tener exactamente 9 digitos")
    @Pattern(regexp = "\\d+", message = "El telefono solo puede contener numeros")
    private String telefono;
    private int edad;
    @NotBlank(message = "El nombre no puede estar vacío")
    private Integer idEntidad;
    @NotBlank(message = "El número de licencia no puede estar vacío")
    private Integer idCamion;
    @Size(max = 20, message = "El número de licencia debe tener como máximo 20 caracteres")
    @NotBlank(message = "El número de licencia no puede estar vacío")
    private String nroLicencia;
}
