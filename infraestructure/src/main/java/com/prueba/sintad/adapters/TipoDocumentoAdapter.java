package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.dto.TipoDocumentoDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.out.TipoDocumentoServiceOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class TipoDocumentoAdapter implements TipoDocumentoServiceOut {
    @Override
    public ResponseApi<TipoDocumentoDTO> saveTipoDocumentoOut(TipoDocumentoDTO tipoDocumentoDTO) {
        return null;
    }

    @Override
    public ResponseApi<TipoDocumentoDTO> updateTipoDocumentoOut(TipoDocumentoDTO tipoDocumentoDTO) {
        return null;
    }

    @Override
    public ResponseApiList<TipoDocumentoDTO> findAllTipoDocumentoOut() {
        return null;
    }
}
