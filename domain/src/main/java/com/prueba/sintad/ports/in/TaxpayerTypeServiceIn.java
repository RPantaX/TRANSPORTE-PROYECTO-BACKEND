package com.prueba.sintad.ports.in;

import com.prueba.sintad.aggregates.dto.TaxpayerTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface TaxpayerTypeServiceIn {

    ResponseApi<TaxpayerTypeDTO> saveTaxpayerTypeIn(TaxpayerTypeDTO taxpayerTypeDTO);
    ResponseApiList<TaxpayerTypeDTO> findAllTaxpayerTypeIn();
}
