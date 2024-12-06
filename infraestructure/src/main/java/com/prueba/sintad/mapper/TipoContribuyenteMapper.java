package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.TipoContribuyenteDTO;
import com.prueba.sintad.entity.TipoContribuyenteEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TipoContribuyenteMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public TipoContribuyenteDTO convertToDto(TipoContribuyenteEntity tipoContribuyenteEntity) {
        return modelMapper.map(tipoContribuyenteEntity, TipoContribuyenteDTO.class);
    }

    public TipoContribuyenteEntity convertToEntity(TipoContribuyenteDTO tipoContribuyenteDTO) {
        return modelMapper.map(tipoContribuyenteDTO, TipoContribuyenteEntity.class);
    }
}
