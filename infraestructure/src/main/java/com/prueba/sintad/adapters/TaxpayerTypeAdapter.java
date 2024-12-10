package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.constants.Paths;
import com.prueba.sintad.aggregates.dto.TaxpayerTypeDTO;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotAcceptableException;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.entity.TaxpayerTypeEntity;
import com.prueba.sintad.mapper.TaxpayerTypeMapper;
import com.prueba.sintad.ports.out.TaxpayerTypeServiceOut;
import com.prueba.sintad.repository.TaxpayerTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaxpayerTypeAdapter implements TaxpayerTypeServiceOut {

    private final TaxpayerTypeRepository taxpayerTypeRepository;
    private final TaxpayerTypeMapper taxpayerTypeMapper;

    @Override
    public ResponseApi<TaxpayerTypeDTO> saveTaxpayerTypeOut(TaxpayerTypeDTO taxpayerTypeDTO) {
        if(taxpayerTypeRepository.existsByName(taxpayerTypeDTO.getName())) {
            log.error("TaxpayerTypeAdapter.saveTaxpayerTypeOut: El nombre ya existe");
            throw new SintadAppNotAcceptableException("El nombre ya existe");
        }
        TaxpayerTypeEntity taxpayerTypeEntity = taxpayerTypeMapper.convertToEntity(taxpayerTypeDTO);
        taxpayerTypeEntity.setState(true);
        TaxpayerTypeEntity taxpayerTypeSaved = taxpayerTypeRepository.save(taxpayerTypeEntity);
        ResponseApi<TaxpayerTypeDTO> responseApi = createGenericResponseApi(taxpayerTypeMapper.convertToDto(taxpayerTypeSaved), Paths.TAXPAYER_TYPE_PATH, Constants.STATUS_CREATED);
        log.info("TaxpayerType saved: {}", taxpayerTypeSaved);
        return responseApi;
    }

    @Override
    public ResponseApiList<TaxpayerTypeDTO> findAllTaxpayerTypeOut() {
        List<TaxpayerTypeEntity> taxpayerTypeDTOList = taxpayerTypeRepository.findAll();
        List<TaxpayerTypeDTO> taxpayerTypeList = taxpayerTypeDTOList.stream().map(taxpayerTypeMapper::convertToDto).toList();
        ResponseApiList<TaxpayerTypeDTO> responseApiList = ResponseApiList.<TaxpayerTypeDTO>builder()
                .status(Constants.STATUS_OK)
                .message(Constants.MESSAGE_OK)
                .timestamp(Constants.getTimestamp())
                .path(Paths.TAXPAYER_TYPE_PATH)
                .data(taxpayerTypeList)
                .build();
        log.info("TaxpayerTypeAdapter.findAllTaxpayerTypeOut: {}", responseApiList);
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
