package com.prueba.sintad.ports.in;

import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.request.RequestUpdateEntidad;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidadListPageable;

public interface EntidadServiceIn {
    ResponseApi<ResponseEntidad> findEntidadByIdIn(Integer id);
    ResponseApi<EntidadDTO> saveEntidadIn(RequestSaveEntidad entidad);
    ResponseApi<EntidadDTO> updateEntidadIn(RequestUpdateEntidad entidad, Integer id);
    ResponseApi<EntidadDTO> deleteEntidadIn(Integer id);
    ResponseApi<ResponseEntidadListPageable> findAllEntidadIn(int pageNumber, int pageSize, String orderBy, String sortDir);
}
