package com.prueba.sintad.aggregates.response;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseEntidadListPageable {
    private List<ResponseEntidad> responseCategoryList;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean end;
}
