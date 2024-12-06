package com.prueba.sintad.aggregates.response;

import lombok.*;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseApi<T> {
    private String status;
    private String message;
    private Timestamp timestamp;
    private T data;
    private String path;
}
