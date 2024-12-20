package com.prueba.sintad.aggregates.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CamioneroDTO {
    private String dni;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private int edad;
    private String nroLicencia;
}
