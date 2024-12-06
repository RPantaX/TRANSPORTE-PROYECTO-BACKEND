package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.entity.EntidadEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EntidadMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public EntidadDTO convertToDto(EntidadEntity entidadEntity) {
        return modelMapper.map(entidadEntity, EntidadDTO.class);
    }

    public EntidadEntity convertToEntity(EntidadDTO entidadDTO) {
        return modelMapper.map(entidadDTO, EntidadEntity.class);
    }
}
