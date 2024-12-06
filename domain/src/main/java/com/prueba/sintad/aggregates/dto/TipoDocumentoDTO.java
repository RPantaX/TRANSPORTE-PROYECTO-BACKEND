package com.prueba.sintad.aggregates.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TipoDocumentoDTO {

    private Integer id;

    private String codigo;

    private String nombre;

    private String descripcion;

    private Boolean estado;
}
