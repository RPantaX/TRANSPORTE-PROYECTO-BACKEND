package com.prueba.sintad.aggregates.response;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseApiList<T> {
    private String status;
    private String message;
    private String timestamp;
    private List<T> data;
    private String path;
}
