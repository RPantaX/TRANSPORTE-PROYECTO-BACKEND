package com.prueba.sintad.ports.in;

import com.prueba.sintad.aggregates.dto.TipoDocumentoDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface TipoDocumentoServiceIn {
    ResponseApi<TipoDocumentoDTO> saveTipoDocumentoIn(TipoDocumentoDTO tipoDocumentoDTO);
    ResponseApi<TipoDocumentoDTO> updateTipoDocumentoIn(TipoDocumentoDTO tipoDocumentoDTO);
    ResponseApiList<TipoDocumentoDTO> findAllTipoDocumentoIn();
}
