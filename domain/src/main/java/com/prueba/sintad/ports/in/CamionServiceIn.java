package com.prueba.sintad.ports.in;

import com.prueba.sintad.aggregates.dto.CamionDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface CamionServiceIn {
    ResponseApi<CamionDTO> findCamionByIdIn(Integer id);
    ResponseApi<CamionDTO> saveCamionIn(CamionDTO camion);
    ResponseApi<String> updateCamionIn(CamionDTO camion, Integer id);
    ResponseApi<String> deleteCamionIn(Integer id);
    ResponseApiList<CamionDTO> findAllCamionIn();
}
