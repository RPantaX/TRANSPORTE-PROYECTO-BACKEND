package com.prueba.sintad.adapters;


import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.constants.Paths;
import com.prueba.sintad.aggregates.dto.CamionDTO;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotFoundException;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseApiList;
import com.prueba.sintad.entity.CamionEntity;
import com.prueba.sintad.mapper.CamionMapper;
import com.prueba.sintad.ports.out.CamionServiceOut;
import com.prueba.sintad.repository.CamionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.prueba.sintad.aggregates.constants.Constants.createGenericResponseApi;

@Slf4j
@Service
@RequiredArgsConstructor
public class CamionAdapter implements CamionServiceOut {

    private final CamionRepository camionRepository;
    private final CamionMapper camionMapper;

    @Override
    public ResponseApi<CamionDTO> findCamionByIdOut(Integer id) {
        CamionEntity camionEntity = camionRepository.findById(id)
                .orElseThrow(() -> new SintadAppNotFoundException("Camion no encontrado"));
        CamionDTO camionDTO = camionMapper.convertToDto(camionEntity);
        String path = Paths.Camion+id;
        ResponseApi<CamionDTO> responseApi = createGenericResponseApi(camionDTO, path, Constants.STATUS_OK);
        log.info("Camion encontrado: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<CamionDTO> saveCamionOut(CamionDTO camion) {
        if(camionRepository.existsByPlaca(camion.getPlaca())){
            throw new SintadAppNotFoundException("Ya existe un camion con la placa: "+camion.getPlaca());
        }
        // Convertir a mayusculas
        camion.setMarca(camion.getMarca().toUpperCase());
        camion.setModelo(camion.getModelo().toUpperCase());
        camion.setPlaca(camion.getPlaca().toUpperCase());

        CamionEntity camionEntity = camionMapper.convertToEntity(camion);
        camionEntity.setCreadoEn(Constants.getTimestamp());
        camionEntity.setEstado(Constants.STATUS_ACTIVE);
        camionRepository.save(camionEntity);
        String path = Paths.Camion;
        ResponseApi<CamionDTO> responseApi = createGenericResponseApi(camion, path, Constants.STATUS_CREATED);
        log.info("Camion guardado: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<String> updateCamionOut(CamionDTO camion, Integer id) {
        if(!camion.getId().equals(id)){
            throw new SintadAppNotFoundException("El id del camion no coincide con el id de la url");
        }
        CamionEntity camionEntity = camionRepository.findById(id)
                .orElseThrow(() -> new SintadAppNotFoundException("Camion no encontrado"));
        camion.setModelo(camion.getModelo().toUpperCase());
        camion.setMarca(camion.getMarca().toUpperCase());
        camion.setPlaca(camion.getPlaca().toUpperCase());
        if(!camionEntity.getPlaca().equals(camion.getPlaca()) && camionRepository.existsByPlaca(camion.getPlaca())){
            throw new SintadAppNotFoundException("Ya existe un camion con la placa: "+camion.getPlaca());
        }

        CamionEntity camionUpdate =  camionRepository.save(camionMapper.convertToEntity(camion));
        camionUpdate.setModificadoEn(Constants.getTimestamp());
        String path = Paths.Camion+id;
        ResponseApi<String> responseApi = createGenericResponseApi("Camion actualizado", path, Constants.STATUS_OK);
        log.info("Camion actualizado: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<String> deleteCamionOut(Integer id) {
        CamionEntity camionEntity = camionRepository.findById(id)
                .orElseThrow(() -> new SintadAppNotFoundException("Camion no encontrado"));
        camionEntity.setEstado(Constants.STATUS_INACTIVE);
        camionEntity.setEliminadoEn(Constants.getTimestamp());
        camionRepository.save(camionEntity);
        String path = Paths.Camion+id;
        ResponseApi<String> responseApi = createGenericResponseApi("Camion eliminado", path, Constants.STATUS_OK);
        log.info("Camion eliminado: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApiList<CamionDTO> findAllCamionOut() {
        log.info("Buscando todos los camiones");
        List<CamionEntity> camionEntities = camionRepository.findAll();
        List<CamionDTO> camionDTOS = camionEntities.stream().map(camionMapper::convertToDto).toList();
        String path = Paths.Camion;
        ResponseApiList<CamionDTO> responseApiList = ResponseApiList.<CamionDTO>builder()
                .path(path)
                .message(Constants.MESSAGE_OK)
                .status(Constants.STATUS_OK)
                .timestamp(Constants.getTimestamp())
                .data(camionDTOS)
                .build();
        log.info("Camiones encontrados: {}", responseApiList);
        return responseApiList;
    }
}
