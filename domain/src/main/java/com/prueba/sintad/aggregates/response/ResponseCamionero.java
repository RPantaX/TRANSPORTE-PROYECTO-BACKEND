package com.prueba.sintad.aggregates.response;

import com.prueba.sintad.aggregates.dto.CamionDTO;
import com.prueba.sintad.aggregates.dto.EntidadDTO;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCamionero {
    private Integer id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private int edad;
    private String nroLicencia;
    private CamionDTO camion;
    private EntidadDTO entidad;
}
