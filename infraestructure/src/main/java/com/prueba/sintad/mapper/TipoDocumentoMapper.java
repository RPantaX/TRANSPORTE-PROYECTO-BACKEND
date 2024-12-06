package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.TipoDocumentoDTO;
import com.prueba.sintad.entity.TipoDocumentoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TipoDocumentoMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public TipoDocumentoDTO convertToDto(TipoDocumentoEntity tipoDocumentoEntity) {
        return modelMapper.map(tipoDocumentoEntity, TipoDocumentoDTO.class);
    }

    public TipoDocumentoEntity convertToEntity(TipoDocumentoDTO tipoDocumentoDTO) {
        return modelMapper.map(tipoDocumentoDTO, TipoDocumentoEntity.class);
    }
}
