package com.prueba.sintad.aggregates.response;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseEntidad {
    private Integer id;

    private String nroDocumento;

    private String razonSocial;

    private String nombreComercial;

    private String direccion;

    private String telefono;

    private Boolean estado;

    private String tipoContribuyente;

    private TipoDocumentoResponse tipoDocumentoResponse;
}
