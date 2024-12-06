package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.dto.TipoDocumentoDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface TipoDocumentoServiceOut {
    ResponseApi<TipoDocumentoDTO> saveTipoDocumentoOut(TipoDocumentoDTO tipoDocumentoDTO);
    ResponseApi<TipoDocumentoDTO> updateTipoDocumentoOut(TipoDocumentoDTO tipoDocumentoDTO);
    ResponseApiList<TipoDocumentoDTO> findAllTipoDocumentoOut();
}
