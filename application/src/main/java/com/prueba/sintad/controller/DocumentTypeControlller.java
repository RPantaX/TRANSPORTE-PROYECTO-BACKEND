package com.prueba.sintad.controller;

import com.prueba.sintad.aggregates.dto.DocumentTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.DocumentTypeServiceIn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/document-type")
@RequiredArgsConstructor
public class DocumentTypeControlller {
    private final DocumentTypeServiceIn service;
    @GetMapping
    public ResponseEntity<ResponseApiList<DocumentTypeDTO>> findAllDocumentTypes(){
        return ResponseEntity.ok(service.findAllDocumentTypeIn());
    }
    @PostMapping
    public ResponseEntity<ResponseApi<DocumentTypeDTO>> saveDocumentType(@Valid @RequestBody DocumentTypeDTO documentTypeDTO){
        return ResponseEntity.ok(service.saveDocumentTypeIn(documentTypeDTO));
    }
}
