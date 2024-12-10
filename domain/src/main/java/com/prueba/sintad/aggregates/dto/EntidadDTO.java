package com.prueba.sintad.aggregates.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EntidadDTO {

    private Integer id;

    private String documentNumber;

    private String legalName;

    private String commercialName;

    private String address;

    private String phone;
}
