package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.CamionDTO;
import com.prueba.sintad.entity.CamionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CamionMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public CamionDTO convertToDto(CamionEntity camionEntity) {
        return modelMapper.map(camionEntity, CamionDTO.class);
    }
    public CamionEntity convertToEntity(CamionDTO camionDTO) {
        return modelMapper.map(camionDTO, CamionEntity.class);
    }
}
