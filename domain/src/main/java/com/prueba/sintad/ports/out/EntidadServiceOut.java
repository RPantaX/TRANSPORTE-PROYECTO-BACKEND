package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.request.RequestUpdateEntidad;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidadListPageable;

public interface EntidadServiceOut {
    ResponseApi<ResponseEntidad> findEntidadByIdOut(Integer id);
    ResponseApi<ResponseEntidad> saveEntidadOut(RequestSaveEntidad entidad);
    ResponseApi<String> updateEntidadOut(RequestUpdateEntidad entidad, Integer id);
    ResponseApi<String> deleteEntidadOut(Integer id);
    ResponseApi<ResponseEntidadListPageable> findAllEntidadOut(int pageNumber, int pageSize, String orderBy, String sortDir);
}
