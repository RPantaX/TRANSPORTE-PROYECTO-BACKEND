package com.prueba.sintad.aggregates.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TipoDocumentoResponse {

    private String codigo;

    private String nombre;

    private String descripcion;


}
