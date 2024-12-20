package com.prueba.sintad.aggregates.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CamionDTO {
    private Integer id;
    private String placa;

    private String marca;

    private String modelo;

    private Integer yearFabricacion;
    private Boolean estado;
}
