package com.prueba.sintad.controller;

import com.prueba.sintad.aggregates.dto.CamionDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.CamionServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camion")
@RequiredArgsConstructor
public class CamionController {
    private final CamionServiceIn camionServiceIn;

    @GetMapping
    public ResponseEntity<ResponseApiList<CamionDTO>> findAllCamiones(){
        return ResponseEntity.ok(camionServiceIn.findAllCamionIn());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<CamionDTO>> findCamionById(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(camionServiceIn.findCamionByIdIn(id));
    }
    @PostMapping
    public ResponseEntity<ResponseApi<CamionDTO>> saveCamion(@RequestBody CamionDTO camionDTO){
        return ResponseEntity.ok(camionServiceIn.saveCamionIn(camionDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseApi<String>> updateCamion(@PathVariable(value = "id") Integer id, @RequestBody CamionDTO camionDTO){
        return ResponseEntity.ok(camionServiceIn.updateCamionIn(camionDTO, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi<String>> deleteCamion(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(camionServiceIn.deleteCamionIn(id));
    }
}
