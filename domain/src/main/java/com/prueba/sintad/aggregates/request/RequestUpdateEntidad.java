package com.prueba.sintad.aggregates.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RequestUpdateEntidad {
    @NotNull(message = "El ID de la entidad no puede ser nulo")
    private Integer id;
    //logica en base a https://www2.sunat.gob.pe/pdt/pdtModulos/independientes/p695/TipoDoc.htm
    @Size(min = 8, max = 15, message = "El número de documento debe tener entre 8 y 15 caracteres")
    @Pattern(regexp = "\\d+", message = "El número de documento solo puede contener dígitos")
    @NotBlank(message = "El número de documento no puede estar vacío")
    private String documentNumber;
    @Size(max = 100, message = "La razón social debe tener entre 1 y 100 caracteres")
    @NotBlank(message = "La razón social no puede estar vacía")
    private String legalName;

    @Size(min = 1, max = 100, message = "El nombre comercial debe tener entre 1 y 100 caracteres")
    private String commercialName;

    @Size(max = 100, message = "La dirección debe tener entre 5 y 100 caracteres")
    private String address;

    @Size(min = 5, max = 50, message = "El teléfono debe tener entre 5 y 50 caracteres")
    @Pattern(regexp = "\\d+", message = "El teléfono solo puede contener dígitos")
    private String phone;
}
