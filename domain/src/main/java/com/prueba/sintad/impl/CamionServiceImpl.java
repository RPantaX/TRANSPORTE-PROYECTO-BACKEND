package com.prueba.sintad.impl;

import com.prueba.sintad.aggregates.dto.CamionDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.CamionServiceIn;
import com.prueba.sintad.ports.out.CamionServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CamionServiceImpl implements CamionServiceIn {

    private final CamionServiceOut camionServiceOut;

    @Override
    public ResponseApi<CamionDTO> findCamionByIdIn(Integer id) {
        return camionServiceOut.findCamionByIdOut(id);
    }

    @Override
    public ResponseApi<CamionDTO> saveCamionIn(CamionDTO camion) {
        return camionServiceOut.saveCamionOut(camion);
    }

    @Override
    public ResponseApi<String> updateCamionIn(CamionDTO camion, Integer id) {
        return camionServiceOut.updateCamionOut(camion, id);
    }

    @Override
    public ResponseApi<String> deleteCamionIn(Integer id) {
        return camionServiceOut.deleteCamionOut(id);
    }

    @Override
    public ResponseApiList<CamionDTO> findAllCamionIn() {
        return camionServiceOut.findAllCamionOut();
    }
}
