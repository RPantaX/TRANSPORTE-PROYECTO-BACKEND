package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.TaxpayerTypeDTO;
import com.prueba.sintad.entity.TaxpayerTypeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TaxpayerTypeMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public TaxpayerTypeDTO convertToDto(TaxpayerTypeEntity taxpayerTypeEntity) {
        return modelMapper.map(taxpayerTypeEntity, TaxpayerTypeDTO.class);
    }

    public TaxpayerTypeEntity convertToEntity(TaxpayerTypeDTO taxpayerTypeDTO) {
        return modelMapper.map(taxpayerTypeDTO, TaxpayerTypeEntity.class);
    }
}
