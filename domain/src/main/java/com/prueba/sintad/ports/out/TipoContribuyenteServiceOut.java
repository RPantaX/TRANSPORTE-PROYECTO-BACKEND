package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.dto.TipoContribuyenteDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface TipoContribuyenteServiceOut {

    ResponseApi<TipoContribuyenteDTO> saveTipoContribuyenteOut(TipoContribuyenteDTO tipoContribuyenteDTO);
    ResponseApi<TipoContribuyenteDTO> updateTipoContribuyenteOut(TipoContribuyenteDTO tipoContribuyenteDTO);
    ResponseApiList<TipoContribuyenteDTO> findAllTipoContribuyenteOut();
}
