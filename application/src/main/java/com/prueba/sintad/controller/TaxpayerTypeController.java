package com.prueba.sintad.controller;

import com.prueba.sintad.aggregates.dto.TaxpayerTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.TaxpayerTypeServiceIn;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/taxpayer-type")
@RequiredArgsConstructor
public class TaxpayerTypeController {
    private final TaxpayerTypeServiceIn service;

    @GetMapping
    public ResponseEntity<ResponseApiList<TaxpayerTypeDTO>> findAllTaxpayerTypes(){
        return ResponseEntity.ok(service.findAllTaxpayerTypeIn());
    }
    @PostMapping
    public ResponseEntity<ResponseApi<TaxpayerTypeDTO>> saveTaxpayerType(@Valid @RequestBody TaxpayerTypeDTO taxpayerTypeDTO){
        return ResponseEntity.ok(service.saveTaxpayerTypeIn(taxpayerTypeDTO));
    }
}
