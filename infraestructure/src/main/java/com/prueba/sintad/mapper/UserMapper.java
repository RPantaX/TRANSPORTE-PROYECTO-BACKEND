package com.prueba.sintad.mapper;

import com.prueba.sintad.aggregates.dto.security.UserDTO;
import com.prueba.sintad.entity.security.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public UserDTO convertToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDTO.class);
    }
    public UserEntity convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserEntity.class);
    }
}
