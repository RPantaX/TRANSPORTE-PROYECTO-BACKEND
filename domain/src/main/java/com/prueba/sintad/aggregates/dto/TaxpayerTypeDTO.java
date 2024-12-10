package com.prueba.sintad.aggregates.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaxpayerTypeDTO {

    private Integer id;
    @Size(min = 2, max = 50, message = "Este campo debe tener entre 2 a 20 caracteres.")
    private String name;
}
