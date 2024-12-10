package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.dto.TaxpayerTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface TaxpayerTypeServiceOut {

    ResponseApi<TaxpayerTypeDTO> saveTaxpayerTypeOut(TaxpayerTypeDTO taxpayerTypeDTO);
    ResponseApiList<TaxpayerTypeDTO> findAllTaxpayerTypeOut();
}
