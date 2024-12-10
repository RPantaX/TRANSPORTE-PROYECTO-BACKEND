package com.prueba.sintad.impl;


import com.prueba.sintad.aggregates.dto.DocumentTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.ports.in.DocumentTypeServiceIn;
import com.prueba.sintad.ports.out.DocumentTypeServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeServiceIn {

    private final DocumentTypeServiceOut documentTypeServiceOut;

    @Override
    public ResponseApi<DocumentTypeDTO> saveDocumentTypeIn(DocumentTypeDTO documentTypeDTO) {
        return documentTypeServiceOut.saveDocumentTypeOut(documentTypeDTO);
    }
    @Override
    public ResponseApiList<DocumentTypeDTO> findAllDocumentTypeIn() {
        return documentTypeServiceOut.findAllDocumentTypeOut();
    }
}
