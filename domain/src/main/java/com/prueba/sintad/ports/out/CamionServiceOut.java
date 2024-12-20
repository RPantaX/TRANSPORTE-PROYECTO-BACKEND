package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.dto.CamionDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface CamionServiceOut {
    ResponseApi<CamionDTO> findCamionByIdOut(Integer id);
    ResponseApi<CamionDTO> saveCamionOut(CamionDTO camion);
    ResponseApi<String> updateCamionOut(CamionDTO camion, Integer id);
    ResponseApi<String> deleteCamionOut(Integer id);
    ResponseApiList<CamionDTO> findAllCamionOut();
}
