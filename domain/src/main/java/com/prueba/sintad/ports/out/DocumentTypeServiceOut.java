package com.prueba.sintad.ports.out;

import com.prueba.sintad.aggregates.dto.DocumentTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface DocumentTypeServiceOut {
    ResponseApi<DocumentTypeDTO> saveDocumentTypeOut(DocumentTypeDTO documentTypeDTO);
    ResponseApiList<DocumentTypeDTO> findAllDocumentTypeOut();
}
