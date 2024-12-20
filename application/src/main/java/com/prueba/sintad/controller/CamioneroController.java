package com.prueba.sintad.controller;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.request.RequestSaveCamionero;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseCamionero;
import com.prueba.sintad.aggregates.response.ResponseCamioneroListPageable;
import com.prueba.sintad.ports.in.CamioneroServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camionero")
@RequiredArgsConstructor
public class CamioneroController {
    private final CamioneroServiceIn service;

    @GetMapping
    public ResponseEntity<ResponseApi<ResponseCamioneroListPageable>> findAllCamioneros(@RequestParam(value = "pageNo", defaultValue = Constants.NUM_PAG_BY_DEFECT, required = false) int pageNo,
                                                                                        @RequestParam(value = "pageSize", defaultValue = Constants.SIZE_PAG_BY_DEFECT, required = false) int pageSize,
                                                                                        @RequestParam(value = "sortBy", defaultValue = Constants.ORDER_BY_DEFECT_ALL, required = false) String sortBy,
                                                                                        @RequestParam(value = "sortDir", defaultValue = Constants.ORDER_DIRECT_BY_DEFECT, required = false) String sortDir){
        return ResponseEntity.ok(service.findAllCamioneroIn(pageNo, pageSize, sortBy, sortDir));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<ResponseCamionero>> getCamioneroById(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(service.findCamioneroByIdIn(id));
    }
    @PostMapping
    public ResponseEntity<ResponseApi<ResponseCamionero>> saveCamionero(@RequestBody RequestSaveCamionero requestSaveCamionero){
        return ResponseEntity.ok(service.saveCamioneroIn(requestSaveCamionero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi<String>> updateCamionero(@PathVariable(value = "id") Integer id, @RequestBody RequestSaveCamionero requestSaveCamionero){
        return ResponseEntity.ok(service.updateCamioneroIn(requestSaveCamionero, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi<String>> deleteCamionero(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(service.deleteCamioneroIn(id));
    }

}
