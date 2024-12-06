package com.prueba.sintad.aggregates.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TipoDocumentoRequest {
    private String codigo;

    private String nombre;

    private String descripcion;
}
