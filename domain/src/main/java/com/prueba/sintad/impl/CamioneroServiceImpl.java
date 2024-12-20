package com.prueba.sintad.impl;

import com.prueba.sintad.aggregates.request.RequestSaveCamionero;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseCamionero;
import com.prueba.sintad.aggregates.response.ResponseCamioneroListPageable;
import com.prueba.sintad.ports.in.CamioneroServiceIn;
import com.prueba.sintad.ports.out.CamioneroServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CamioneroServiceImpl implements CamioneroServiceIn {

    private final CamioneroServiceOut camioneroServiceOut;

    @Override
    public ResponseApi<ResponseCamionero> findCamioneroByIdIn(Integer id) {
        return camioneroServiceOut.findCamioneroByIdOut(id);
    }

    @Override
    public ResponseApi<ResponseCamionero> saveCamioneroIn(RequestSaveCamionero camionero) {
        return camioneroServiceOut.saveCamioneroOut(camionero);
    }

    @Override
    public ResponseApi<String> updateCamioneroIn(RequestSaveCamionero camionero, Integer id) {
        return camioneroServiceOut.updateCamioneroOut(camionero, id);
    }

    @Override
    public ResponseApi<String> deleteCamioneroIn(Integer id) {
        return camioneroServiceOut.deleteCamioneroOut(id);
    }

    @Override
    public ResponseApi<ResponseCamioneroListPageable> findAllCamioneroIn(int pageNumber, int pageSize, String orderBy, String sortDir) {
        return camioneroServiceOut.findAllCamioneroOut(pageNumber, pageSize, orderBy, sortDir);
    }
}
