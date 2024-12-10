package com.prueba.sintad.aggregates.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RequestTaxpayerType {
    @Size(max = 50, message = "El nombre del tipo de contribuyente debe tener como máximo 50 caracteres")
    @NotBlank(message = "El nombre del tipo de contribuyente no puede estar vacío")
    private String name;
}
