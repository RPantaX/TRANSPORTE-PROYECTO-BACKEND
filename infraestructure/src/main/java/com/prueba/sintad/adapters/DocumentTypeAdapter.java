package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.constants.Paths;
import com.prueba.sintad.aggregates.dto.DocumentTypeDTO;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotAcceptableException;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;

import com.prueba.sintad.entity.DocumentTypeEntity;
import com.prueba.sintad.mapper.DocumentTypeMapper;
import com.prueba.sintad.ports.out.DocumentTypeServiceOut;
import com.prueba.sintad.repository.DocumentTypeRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentTypeAdapter implements DocumentTypeServiceOut {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeMapper documentTypeMapper;

    @Override
    public ResponseApi<DocumentTypeDTO> saveDocumentTypeOut(DocumentTypeDTO documentTypeDTO) {
        if(documentTypeRepository.existsByCodeOrName(documentTypeDTO.getCode(), documentTypeDTO.getName())) throw new SintadAppNotAcceptableException("El código o nombre ya existe");
        DocumentTypeEntity documentTypeEntity = documentTypeMapper.convertToEntity(documentTypeDTO);
        documentTypeEntity.setState(true);
        DocumentTypeEntity documentTypeSaved = documentTypeRepository.save(documentTypeEntity);

        ResponseApi<DocumentTypeDTO> responseApi = createGenericResponseApi(documentTypeMapper.convertToDto(documentTypeSaved), Paths.DOCUMENT_TYPE_PATH, Constants.STATUS_CREATED);
        log.info("DocumentType saved: {}", documentTypeSaved);
        return responseApi;
    }

    @Override
    public ResponseApiList<DocumentTypeDTO> findAllDocumentTypeOut() {
        List<DocumentTypeEntity> documentTypeDTOList = documentTypeRepository.findAll();
        List<DocumentTypeDTO> documentTypeList = documentTypeDTOList.stream().map(documentTypeMapper::convertToDto).toList();

        ResponseApiList<DocumentTypeDTO> responseApiList = ResponseApiList.<DocumentTypeDTO>builder()
                .status(Constants.STATUS_OK)
                .message(Constants.MESSAGE_OK)
                .timestamp(Constants.getTimestamp())
                .path(Paths.DOCUMENT_TYPE_PATH)
                .data(documentTypeList)
                .build();
        log.info("DocumentTypeAdapter.findAllDocumentTypeOut: {}", responseApiList);
        return responseApiList;
    }
    private <T> ResponseApi<T> createGenericResponseApi(T data, String path, String status) {
        return ResponseApi.<T>builder()
                .path(path)
                .message(Constants.MESSAGE_OK)
                .status(status)
                .timestamp(Constants.getTimestamp())
                .data(data)
                .build();
    }
}
