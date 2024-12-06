package com.prueba.sintad.ports.in;

import com.prueba.sintad.aggregates.dto.TipoContribuyenteDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface TipoContribuyenteServiceIn {

    ResponseApi<TipoContribuyenteDTO> saveTipoContribuyenteIn(TipoContribuyenteDTO tipoContribuyenteDTO);
    ResponseApi<TipoContribuyenteDTO> updateTipoContribuyenteIn(TipoContribuyenteDTO tipoContribuyenteDTO);
    ResponseApiList<TipoContribuyenteDTO> findAllTipoContribuyenteIn();
}
