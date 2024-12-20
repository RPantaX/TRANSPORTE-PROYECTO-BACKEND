package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.request.RequestSaveCamionero;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseCamionero;
import com.prueba.sintad.aggregates.response.ResponseCamioneroListPageable;

public interface CamioneroServiceOut {
    ResponseApi<ResponseCamionero> findCamioneroByIdOut(Integer id);
    ResponseApi<ResponseCamionero> saveCamioneroOut(RequestSaveCamionero camionero);
    ResponseApi<String> updateCamioneroOut(RequestSaveCamionero camionero, Integer id);
    ResponseApi<String> deleteCamioneroOut(Integer id);
    ResponseApi<ResponseCamioneroListPageable> findAllCamioneroOut(int pageNumber, int pageSize, String orderBy, String sortDir);
}
