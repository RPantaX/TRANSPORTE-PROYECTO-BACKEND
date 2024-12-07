package com.prueba.sintad.aggregates.response.error;

import lombok.*;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseError<T> {
    private String status;
    private T message;
    private Timestamp timestamp;
    private String path;
}
