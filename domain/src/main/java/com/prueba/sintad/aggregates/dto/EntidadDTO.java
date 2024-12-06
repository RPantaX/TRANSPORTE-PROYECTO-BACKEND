package com.prueba.sintad.aggregates.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EntidadDTO {

    private Integer id;

    private String nroDocumento;

    private String razonSocial;

    private String nombreComercial;

    private String direccion;

    private String telefono;

    private Boolean estado;
}
