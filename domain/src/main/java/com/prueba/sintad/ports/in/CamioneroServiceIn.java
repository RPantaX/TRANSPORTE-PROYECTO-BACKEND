package com.prueba.sintad.ports.in;

import com.prueba.sintad.aggregates.request.RequestSaveCamionero;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseCamionero;
import com.prueba.sintad.aggregates.response.ResponseCamioneroListPageable;

public interface CamioneroServiceIn {
    ResponseApi<ResponseCamionero> findCamioneroByIdIn(Integer id);
    ResponseApi<ResponseCamionero> saveCamioneroIn(RequestSaveCamionero camionero);
    ResponseApi<String> updateCamioneroIn(RequestSaveCamionero camionero, Integer id);
    ResponseApi<String> deleteCamioneroIn(Integer id);
    ResponseApi<ResponseCamioneroListPageable> findAllCamioneroIn(int pageNumber, int pageSize, String orderBy, String sortDir);
}
