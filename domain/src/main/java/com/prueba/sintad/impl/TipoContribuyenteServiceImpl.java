package com.prueba.sintad.impl;

import com.prueba.sintad.aggregates.dto.TipoContribuyenteDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.TipoContribuyenteServiceIn;
import com.prueba.sintad.ports.out.TipoContribuyenteServiceOut;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TipoContribuyenteServiceImpl implements TipoContribuyenteServiceIn {

    private final TipoContribuyenteServiceOut tipoContribuyenteServiceOut;

    @Override
    public ResponseApi<TipoContribuyenteDTO> saveTipoContribuyenteIn(TipoContribuyenteDTO tipoContribuyenteDTO) {
        return tipoContribuyenteServiceOut.saveTipoContribuyenteOut(tipoContribuyenteDTO);
    }

    @Override
    public ResponseApi<TipoContribuyenteDTO> updateTipoContribuyenteIn(TipoContribuyenteDTO tipoContribuyenteDTO) {
        return tipoContribuyenteServiceOut.updateTipoContribuyenteOut(tipoContribuyenteDTO);
    }

    @Override
    public ResponseApiList<TipoContribuyenteDTO> findAllTipoContribuyenteIn() {
        return tipoContribuyenteServiceOut.findAllTipoContribuyenteOut();
    }
}
