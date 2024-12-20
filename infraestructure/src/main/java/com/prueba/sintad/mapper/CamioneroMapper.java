package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.CamioneroDTO;
import com.prueba.sintad.entity.CamioneroEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CamioneroMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public CamioneroDTO convertToDto(CamioneroEntity camioneroEntity) {
        return modelMapper.map(camioneroEntity, CamioneroDTO.class);
    }
    public CamioneroEntity convertToEntity(CamioneroDTO camioneroDTO) {
        return modelMapper.map(camioneroDTO, CamioneroEntity.class);
    }
}
