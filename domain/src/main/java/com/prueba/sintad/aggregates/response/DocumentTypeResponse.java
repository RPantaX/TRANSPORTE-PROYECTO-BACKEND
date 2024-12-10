package com.prueba.sintad.aggregates.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentTypeResponse {
    private String code;
    private String name;
    private String description;

}
