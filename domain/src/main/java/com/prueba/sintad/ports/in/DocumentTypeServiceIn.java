package com.prueba.sintad.ports.in;

import com.prueba.sintad.aggregates.dto.DocumentTypeDTO;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

public interface DocumentTypeServiceIn {
    ResponseApi<DocumentTypeDTO> saveDocumentTypeIn(DocumentTypeDTO documentTypeDTO);
    ResponseApiList<DocumentTypeDTO> findAllDocumentTypeIn();
}
