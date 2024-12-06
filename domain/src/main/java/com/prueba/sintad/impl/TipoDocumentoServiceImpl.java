package com.prueba.sintad.impl;


import com.prueba.sintad.aggregates.dto.TipoDocumentoDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.TipoDocumentoServiceIn;
import com.prueba.sintad.ports.out.TipoDocumentoServiceOut;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TipoDocumentoServiceImpl implements TipoDocumentoServiceIn {

    private final TipoDocumentoServiceOut tipoDocumentoServiceOut;

    @Override
    public ResponseApi<TipoDocumentoDTO> saveTipoDocumentoIn(TipoDocumentoDTO tipoDocumentoDTO) {
        return tipoDocumentoServiceOut.saveTipoDocumentoOut(tipoDocumentoDTO);
    }

    @Override
    public ResponseApi<TipoDocumentoDTO> updateTipoDocumentoIn(TipoDocumentoDTO tipoDocumentoDTO) {
        return tipoDocumentoServiceOut.updateTipoDocumentoOut(tipoDocumentoDTO);
    }

    @Override
    public ResponseApiList<TipoDocumentoDTO> findAllTipoDocumentoIn() {
        return tipoDocumentoServiceOut.findAllTipoDocumentoOut();
    }
}
