package com.prueba.sintad.aggregates.response;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCamioneroListPageable {
    private List<ResponseCamionero> responseCamioneroList;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean end;
}
