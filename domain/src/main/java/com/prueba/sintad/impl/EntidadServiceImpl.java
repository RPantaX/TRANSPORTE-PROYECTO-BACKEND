package com.prueba.sintad.impl;

import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.request.RequestUpdateEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.ResponseEntidadListPageable;
import com.prueba.sintad.ports.in.EntidadServiceIn;
import com.prueba.sintad.ports.out.EntidadServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntidadServiceImpl implements EntidadServiceIn {

    private final EntidadServiceOut entidadServiceOut;

    @Override
    public ResponseApi<ResponseEntidad> findEntidadByIdIn(Integer id) {
        return entidadServiceOut.findEntidadByIdOut(id);
    }

    @Override
    public ResponseApi<EntidadDTO> saveEntidadIn(RequestSaveEntidad entidad) {
        return entidadServiceOut.saveEntidadOut(entidad);
    }

    @Override
    public ResponseApi<String> updateEntidadIn(RequestUpdateEntidad entidad, Integer id) {
        return entidadServiceOut.updateEntidadOut(entidad, id);
    }

    @Override
    public ResponseApi<String> deleteEntidadIn(Integer id) {
        return entidadServiceOut.deleteEntidadOut(id);
    }

    @Override
    public ResponseApi<ResponseEntidadListPageable> findAllEntidadIn(int pageNumber, int pageSize, String orderBy, String sortDir) {
        return entidadServiceOut.findAllEntidadOut(pageNumber, pageSize, orderBy, sortDir);
    }
}
