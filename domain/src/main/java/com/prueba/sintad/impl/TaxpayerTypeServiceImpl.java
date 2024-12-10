package com.prueba.sintad.impl;

import com.prueba.sintad.aggregates.dto.TaxpayerTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.TaxpayerTypeServiceIn;
import com.prueba.sintad.ports.out.TaxpayerTypeServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxpayerTypeServiceImpl implements TaxpayerTypeServiceIn {

    private final TaxpayerTypeServiceOut taxpayerTypeServiceOut;

    @Override
    public ResponseApi<TaxpayerTypeDTO> saveTaxpayerTypeIn(TaxpayerTypeDTO taxpayerTypeDTO) {
        return taxpayerTypeServiceOut.saveTaxpayerTypeOut(taxpayerTypeDTO);
    }
    @Override
    public ResponseApiList<TaxpayerTypeDTO> findAllTaxpayerTypeIn() {
        return taxpayerTypeServiceOut.findAllTaxpayerTypeOut();
    }
}
