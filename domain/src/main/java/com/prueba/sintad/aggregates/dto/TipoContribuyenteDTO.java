package com.prueba.sintad.aggregates.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TipoContribuyenteDTO {
    private Integer id;

    private String nombre;

    private Boolean estado;
}
