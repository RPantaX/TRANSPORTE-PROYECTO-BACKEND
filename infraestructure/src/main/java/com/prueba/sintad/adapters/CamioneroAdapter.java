package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.constants.Paths;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotFoundException;
import com.prueba.sintad.aggregates.request.RequestSaveCamionero;
import com.prueba.sintad.aggregates.response.*;
import com.prueba.sintad.aggregates.response.rest.ResponseReniec;
import com.prueba.sintad.entity.CamioneroEntity;
import com.prueba.sintad.mapper.CamionMapper;
import com.prueba.sintad.mapper.CamioneroMapper;
import com.prueba.sintad.mapper.EntidadMapper;
import com.prueba.sintad.ports.out.CamioneroServiceOut;
import com.prueba.sintad.repository.CamionRepository;
import com.prueba.sintad.repository.CamioneroRepository;
import com.prueba.sintad.repository.EntidadRepository;
import com.prueba.sintad.rest.client.ReniecClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j //para los logs
@Service
@RequiredArgsConstructor
public class CamioneroAdapter implements CamioneroServiceOut {

    private final CamioneroRepository camioneroRepository;
    private final CamionRepository camionRepository;
    private final EntidadRepository entidadRepository;
    private final CamioneroMapper camioneroMapper;
    private final CamionMapper camionMapper;
    private final EntidadMapper entidadMapper;
    private final ReniecClient reniec;

    @Value("${token.rest.api}")
    String tokenApi;

    @Override
    public ResponseApi<ResponseCamionero> findCamioneroByIdOut(Integer id) {
        CamioneroEntity camioneroEntity = camioneroRepository.findById(id)
                .orElseThrow(()-> new SintadAppNotFoundException("Camionero no encontrado"));

        ResponseCamionero responseCamionero = createResponseCamionero(camioneroEntity);
        String path = Paths.Camionero;
        ResponseApi<ResponseCamionero> responseApi = Constants.createGenericResponseApi(responseCamionero,path, Constants.STATUS_OK);
        log.info("RESPONSE API: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<ResponseCamionero> saveCamioneroOut(RequestSaveCamionero camionero) {
        if(camioneroRepository.existsByDni(camionero.getDni())){
            throw new SintadAppNotFoundException("El DNI ya se encuentra registrado");
        }
        if(camioneroRepository.existsByNroLicencia(camionero.getNroLicencia())){
            throw new SintadAppNotFoundException("El Nro de Licencia ya se encuentra registrado");
        }
        ResponseReniec responseReniec = getExecutionReniec(camionero.getDni());
        camionero.setDireccion(camionero.getDireccion().toUpperCase());
        CamioneroEntity camioneroSave = createCamioneroForSave(camionero,responseReniec);
        camioneroRepository.save(camioneroSave);
        log.info("CAMIONERO GUARDADO: {}", camioneroSave);
        ResponseCamionero responseCamionero = createResponseCamionero(camioneroSave);
        String path = Paths.Camionero;
        ResponseApi<ResponseCamionero> responseApi = Constants.createGenericResponseApi(responseCamionero,path, Constants.STATUS_CREATED);
        log.info("RESPONSE API: {}", responseApi);
        return responseApi;
    }



    @Override
    public ResponseApi<String> updateCamioneroOut(RequestSaveCamionero camionero, Integer id) {
        if(!camionero.getId().equals(id))
            throw new SintadAppNotFoundException("El id del camionero no coincide con el id de la url");
        CamioneroEntity camioneroEntity = camioneroRepository.findById(id)
                .orElseThrow(()-> new SintadAppNotFoundException("Camionero no encontrado"));
        if(camioneroEntity.getDni().equals(camionero.getDni())){
            if(!camionero.getNroLicencia().equals(camioneroEntity.getNroLicencia()) && camioneroRepository.existsByNroLicencia(camionero.getNroLicencia())){
                throw new SintadAppNotFoundException("El Nro de Licencia ya se encuentra registrado");
            }
            camioneroEntity.setDireccion(camionero.getDireccion().toUpperCase());
            camioneroEntity.setTelefono(camionero.getTelefono());
            camioneroEntity.setEdad(camionero.getEdad());
            camioneroEntity.setNroLicencia(camionero.getNroLicencia());
            camioneroEntity.setModificadoEn(Constants.getTimestamp());
            camioneroRepository.save(camioneroEntity);
            log.info("CAMIONERO ACTUALIZADO: {}", camioneroEntity);
            String path = Paths.Camionero;
            ResponseApi<String> responseApi = Constants.createGenericResponseApi(Constants.OPERATION_SUCCESS,path, Constants.STATUS_OK);
            log.info("RESPONSE API: {}", responseApi);
            return responseApi;
        }
        if(!camionero.getNroLicencia().equals(camioneroEntity.getNroLicencia()) && camioneroRepository.existsByNroLicencia(camionero.getNroLicencia())){
            throw new SintadAppNotFoundException("El Nro de Licencia ya se encuentra registrado");
        }
        ResponseReniec responseReniec = getExecutionReniec(camionero.getDni());
        camionero.setDireccion(camionero.getDireccion().toUpperCase());
        camioneroEntity.setDni(camionero.getDni());
        camioneroEntity.setNombres(responseReniec.getNombres());
        camioneroEntity.setApellidos(responseReniec.getApellidoPaterno()+" "+responseReniec.getApellidoMaterno());
        camioneroEntity.setDireccion(camionero.getDireccion());
        camioneroEntity.setTelefono(camionero.getTelefono());
        camioneroEntity.setEdad(camionero.getEdad());
        camioneroEntity.setNroLicencia(camionero.getNroLicencia());
        camioneroEntity.setModificadoEn(Constants.getTimestamp());
        camioneroRepository.save(camioneroEntity);
        log.info("CAMIONERO ACTUALIZADO: {}", camioneroEntity);
        String path = Paths.Camionero;
        ResponseApi<String> responseApi = Constants.createGenericResponseApi(Constants.OPERATION_SUCCESS,path, Constants.STATUS_OK);
        log.info("RESPONSE API: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<String> deleteCamioneroOut(Integer id) {
        CamioneroEntity camioneroEntity = camioneroRepository.findById(id)
                .orElseThrow(()-> new SintadAppNotFoundException("Camionero no encontrado"));
        camioneroEntity.setEstado(Constants.STATUS_INACTIVE);
        camioneroEntity.setEliminadoEn(Constants.getTimestamp());
        camioneroRepository.save(camioneroEntity);
        log.info("CAMIONERO ELIMINADO: {}", camioneroEntity);
        String path = Paths.Camionero;
        ResponseApi<String> responseApi = Constants.createGenericResponseApi("Camionero eliminado",path, Constants.STATUS_OK);
        log.info("RESPONSE API: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<ResponseCamioneroListPageable> findAllCamioneroOut(int pageNumber, int pageSize, String orderBy, String sortDir) {
        log.info("PAGINA: {} TAMAÑO: {} ORDENAR POR: {} DIRECCION: {}",pageNumber,pageSize,orderBy,sortDir);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() :
                Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<CamioneroEntity> camioneroEntityPage = camioneroRepository.findAllPageableByEstado(Constants.STATUS_ACTIVE, pageable);
        String path = Paths.Camionero;
        List<ResponseCamionero> responseCamioneroList = camioneroEntityPage.map(this::getCamionero).getContent();
        ResponseCamioneroListPageable responseCamioneroListPageable = ResponseCamioneroListPageable.builder()
                .responseCamioneroList(responseCamioneroList)
                .pageSize(camioneroEntityPage.getSize())
                .end(camioneroEntityPage.isLast())
                .totalPages(camioneroEntityPage.getTotalPages())
                .pageNumber(camioneroEntityPage.getNumber())
                .totalElements(camioneroEntityPage.getTotalElements())
                .build();
        ResponseApi<ResponseCamioneroListPageable> responseApi = Constants.createGenericResponseApi(responseCamioneroListPageable,path, Constants.STATUS_OK);
        log.info("RESPONSE CAMIONERO LIST PAGEABLE: {}", responseApi);
        return responseApi;
    }
    private ResponseCamionero getCamionero(CamioneroEntity camioneroEntity) {
        ResponseCamionero responseCamionero = responseCamioneroList(camioneroEntity);
        log.info("ENTIDAD: {}", responseCamionero);
        return responseCamionero;
    }

    private ResponseCamionero responseCamioneroList(CamioneroEntity camioneroEntity) {
        return ResponseCamionero.builder()
                .id(camioneroEntity.getId())
                .dni(camioneroEntity.getDni())
                .nombres(camioneroEntity.getNombres())
                .apellidos(camioneroEntity.getApellidos())
                .direccion(camioneroEntity.getDireccion())
                .telefono(camioneroEntity.getTelefono())
                .edad(camioneroEntity.getEdad())
                .nroLicencia(camioneroEntity.getNroLicencia())
                .camion(camionMapper.convertToDto(camioneroEntity.getCamion()))
                .entidad(entidadMapper.convertToDto(camioneroEntity.getEntidad()))
                .build();
    }

    public ResponseReniec getExecutionReniec(String numero){
        String authorization = "Bearer "+tokenApi;
        ResponseReniec responseReniec = reniec.getInfoReniec(numero,authorization);
        log.info("RESPONSE RENIEC: {}", responseReniec);
        return  responseReniec;
    }
    private CamioneroEntity createCamioneroForSave(RequestSaveCamionero camionero, ResponseReniec responseReniec) {
        return CamioneroEntity.builder()
                .dni(camionero.getDni())
                .nombres(responseReniec.getNombres())
                .apellidos(responseReniec.getApellidoPaterno()+" "+responseReniec.getApellidoMaterno())
                .direccion(camionero.getDireccion())
                .telefono(camionero.getTelefono())
                .edad(camionero.getEdad())
                .nroLicencia(camionero.getNroLicencia())
                .camion(camionRepository.findById(camionero.getIdCamion())
                        .orElseThrow(()-> new SintadAppNotFoundException("Camion no encontrado")))
                .entidad(entidadRepository.findById(camionero.getIdEntidad())
                        .orElseThrow(()-> new SintadAppNotFoundException("Entidad no encontrada")))
                .creadoEn(Constants.getTimestamp())
                .estado(Constants.STATUS_ACTIVE)
                .build();
    }

    private ResponseCamionero createResponseCamionero(CamioneroEntity camioneroEntity) {
        return ResponseCamionero.builder()
                .id(camioneroEntity.getId())
                .dni(camioneroEntity.getDni())
                .nombres(camioneroEntity.getNombres())
                .apellidos(camioneroEntity.getApellidos())
                .direccion(camioneroEntity.getDireccion())
                .telefono(camioneroEntity.getTelefono())
                .edad(camioneroEntity.getEdad())
                .nroLicencia(camioneroEntity.getNroLicencia())
                .camion(camionMapper.convertToDto(camioneroEntity.getCamion()))
                .entidad(entidadMapper.convertToDto(camioneroEntity.getEntidad()))
                .build();
    }
}
