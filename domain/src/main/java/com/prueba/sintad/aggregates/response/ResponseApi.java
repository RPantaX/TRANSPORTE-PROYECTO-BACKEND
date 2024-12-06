package com.prueba.sintad.aggregates.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseApi<T> {
    private String status;
    private String message;
    private String timestamp;
    private T data;
    private String path;
}
