package com.prueba.sintad.aggregates.response;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseEntidad {
    private Integer id;

    private String documentNumber;

    private String legalName;

    private String commercialName;

    private String address;

    private String phone;

    private String taxpayerType;

    private DocumentTypeResponse documentTypeResponse;
}
