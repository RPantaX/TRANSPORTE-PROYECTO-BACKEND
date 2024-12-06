package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.request.RequestUpdateEntidad;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidadListPageable;

public interface EntidadServiceOut {
    ResponseApi<ResponseEntidad> findEntidadByIdOut(Integer id);
    ResponseApi<EntidadDTO> saveEntidadOut(RequestSaveEntidad entidad);
    ResponseApi<EntidadDTO> updateEntidadOut(RequestUpdateEntidad entidad, Integer id);
    ResponseApi<EntidadDTO> deleteEntidadOut(Integer id);
    ResponseApi<ResponseEntidadListPageable> findAllEntidadOut(int pageNumber, int pageSize, String orderBy, String sortDir);
}
