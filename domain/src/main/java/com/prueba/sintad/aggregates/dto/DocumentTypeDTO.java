package com.prueba.sintad.aggregates.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentTypeDTO {

    private Integer id;
    @Size(max = 20, message = "El número de documento debe tener entre 8 y 15 caracteres")
    @Pattern(regexp = "\\d+", message = "El número de documento solo puede contener numeros")
    @NotBlank(message = "El número de documento no puede estar vacío")
    private String code;
    @Size(min = 2, max = 100, message = "Este campo debe tener entre 2 a 100 caracteres.")
    private String name;
    @Size(min = 2, max = 200, message = "Este campo debe tener entre 2 a 200 caracteres.")
    private String description;
}
