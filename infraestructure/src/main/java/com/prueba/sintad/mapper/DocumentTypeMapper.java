package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.DocumentTypeDTO;
import com.prueba.sintad.entity.DocumentTypeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DocumentTypeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public DocumentTypeDTO convertToDto(DocumentTypeEntity documentTypeEntity) {
        return modelMapper.map(documentTypeEntity, DocumentTypeDTO.class);
    }

    public DocumentTypeEntity convertToEntity(DocumentTypeDTO documentTypeDTO) {
        return modelMapper.map(documentTypeDTO, DocumentTypeEntity.class);
    }
}
