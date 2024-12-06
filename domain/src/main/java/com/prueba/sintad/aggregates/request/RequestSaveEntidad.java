package com.prueba.sintad.aggregates.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RequestSaveEntidad {

    private String nroDocumento;

    private String razonSocial;

    private String nombreComercial;

    private String direccion;

    private String telefono;

    private Integer idTipoDocumento;

    private Integer idTipoContribuyente;

}
