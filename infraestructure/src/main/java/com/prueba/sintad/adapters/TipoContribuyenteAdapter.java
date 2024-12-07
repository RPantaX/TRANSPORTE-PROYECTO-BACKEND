package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.dto.TipoContribuyenteDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.out.TipoContribuyenteServiceOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TipoContribuyenteAdapter implements TipoContribuyenteServiceOut {
    @Override
    public ResponseApi<TipoContribuyenteDTO> saveTipoContribuyenteOut(TipoContribuyenteDTO tipoContribuyenteDTO) {
        return null;
    }

    @Override
    public ResponseApi<TipoContribuyenteDTO> updateTipoContribuyenteOut(TipoContribuyenteDTO tipoContribuyenteDTO) {
        return null;
    }

    @Override
    public ResponseApiList<TipoContribuyenteDTO> findAllTipoContribuyenteOut() {
        return null;
    }
}
