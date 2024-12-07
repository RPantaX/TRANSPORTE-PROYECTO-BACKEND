package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.constants.Paths;
import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotAcceptableException;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotFoundException;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.request.RequestUpdateEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.ResponseEntidadListPageable;
import com.prueba.sintad.aggregates.response.TipoDocumentoResponse;
import com.prueba.sintad.aggregates.response.rest.ResponseReniec;
import com.prueba.sintad.aggregates.response.rest.ResponseSunat;

import com.prueba.sintad.entity.EntidadEntity;
import com.prueba.sintad.entity.TipoContribuyenteEntity;
import com.prueba.sintad.entity.TipoDocumentoEntity;

import com.prueba.sintad.repository.EntidadRepository;
import com.prueba.sintad.repository.TipoContribuyenteRepository;
import com.prueba.sintad.repository.TipoDocumentoRepository;

import com.prueba.sintad.rest.client.ReniecClient;
import com.prueba.sintad.rest.client.SunatClient;

import com.prueba.sintad.mapper.EntidadMapper;
import com.prueba.sintad.ports.out.EntidadServiceOut;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntidadAdapter implements EntidadServiceOut {

    private final EntidadRepository entidadRepository;
    private final TipoContribuyenteRepository tipoContribuyenteRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final EntidadMapper entidadMapper;
    private final ReniecClient reniec;
    private final SunatClient sunat;

    @Value("${token.rest.api}")
    String tokenApi;

    @Override
    public ResponseApi<ResponseEntidad> findEntidadByIdOut(Integer id) {
        EntidadEntity entidadEntity = entidadRepository.findEntidadById(id).orElseThrow( () -> new SintadAppNotFoundException("Entidad no encontrada"));

        ResponseEntidad responseEntidad = getEntidad(entidadEntity);

        String path = Paths.Entidad + id;
        ResponseApi<ResponseEntidad> responseApi = createGenericResponseApi(responseEntidad,path, Constants.STATUS_OK);
        log.info("RESPONSE ENTIDAD: {}", responseApi);
        return responseApi;
    }
    @Override
    public ResponseApi<EntidadDTO> saveEntidadOut(RequestSaveEntidad entidad) {
        if(entidadRepository.existsByNroDocumento(entidad.getNroDocumento()))
            throw new SintadAppNotAcceptableException("Entidad ya existe");
        TipoDocumentoEntity tipoDocumentoEntity = tipoDocumentoRepository.findById(entidad.getIdTipoDocumento())
                .orElseThrow( () -> new SintadAppNotAcceptableException("Tipo de documento no existe"));
        TipoContribuyenteEntity tipoContribuyenteEntity = tipoContribuyenteRepository.findById(entidad.getIdTipoContribuyente())
                .orElseThrow( () -> new SintadAppNotAcceptableException("Tipo de contribuyente no existe"));
        // Convertir campos a mayúsculas
        entidad.setRazonSocial(entidad.getRazonSocial().toUpperCase());
        entidad.setDireccion(entidad.getDireccion().toUpperCase());
        entidad.setNombreComercial(entidad.getNombreComercial().toUpperCase());

        validateRazonSocial(entidad.getNroDocumento(), entidad.getRazonSocial(), tipoDocumentoEntity);

        EntidadEntity entidadEntity = createEntidadEntity(entidad, tipoDocumentoEntity, tipoContribuyenteEntity);
        EntidadEntity entidadSaved = entidadRepository.save(entidadEntity);
        EntidadDTO entidadDTO = entidadMapper.convertToDto(entidadSaved);
        String path = Paths.Entidad;
        ResponseApi<EntidadDTO> responseApi = createGenericResponseApi(entidadDTO,path, Constants.STATUS_CREATED);
        log.info("RESPONSE ENTIDAD: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<String> updateEntidadOut(RequestUpdateEntidad entidad, Integer id) {
        if(!entidad.getId().equals(id))
            throw new SintadAppNotAcceptableException("El id del path con el id de la entidad no coincide");
        EntidadEntity entidadEntity = entidadRepository.findEntidadById(id)
                .orElseThrow( () -> new SintadAppNotFoundException("Entidad no encontrada"));

        // Convertir campos a mayúsculas
        entidad.setRazonSocial(entidad.getRazonSocial().toUpperCase());
        entidad.setDireccion(entidad.getDireccion().toUpperCase());
        entidad.setNombreComercial(entidad.getNombreComercial().toUpperCase());

        if(!entidadEntity.getNroDocumento().equalsIgnoreCase(entidad.getNroDocumento())){
            //Validar si el nuevo nroDocumento ya existe
            if(entidadRepository.existsByNroDocumento(entidad.getNroDocumento()))
                throw new SintadAppNotAcceptableException("Entidad ya existe");
            validateRazonSocial(entidad.getNroDocumento(), entidad.getRazonSocial(), entidadEntity.getTipoDocumento());
        }
        else{
            //Si el nroDocumento es el mismo no tiene que validar si el nroDocumento ya existe
            validateRazonSocial(entidad.getNroDocumento(), entidad.getRazonSocial(), entidadEntity.getTipoDocumento());
        }
        entidadRepository.updateEntidadById(id, entidad.getNroDocumento(), entidad.getRazonSocial(), entidad.getNombreComercial(), entidad.getDireccion(), entidad.getTelefono());
        log.info("Entidad actualizada de {} a {}", entidadEntity, entidad);
        String path = Paths.Entidad + id;
        ResponseApi<String> responseApi = createGenericResponseApi(Constants.OPERATION_SUCCESS,path, Constants.STATUS_OK);
        log.info("RESPONSE ENTIDAD: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<String> deleteEntidadOut(Integer id) {
        if (!entidadRepository.existsById(id)) throw new SintadAppNotFoundException("Entidad no encontrada");
        //eliminado logico
        entidadRepository.deleteEntidadById(id);
        String path = Paths.Entidad + id;
        ResponseApi<String> responseApi = createGenericResponseApi(Constants.OPERATION_SUCCESS,path, Constants.STATUS_OK);
        log.info("RESPONSE ENTIDAD: {}", responseApi);
        return responseApi;
    }

    @Override
    public ResponseApi<ResponseEntidadListPageable> findAllEntidadOut(int pageNumber, int pageSize, String orderBy, String sortDir) {
        log.info("Searching all products with the following parameters: {}",Constants.parametersForLogger(pageNumber, pageSize, orderBy, sortDir));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() :
                Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<EntidadEntity> entidadPageableList = entidadRepository.findAllPageableByEstado(Constants.STATUS_ACTIVE, pageable);
        String path = Paths.Entidad;

        List<ResponseEntidad> responseEntidadList = entidadPageableList.map(this::getEntidad).getContent();

        ResponseEntidadListPageable responseEntidadListPageable = ResponseEntidadListPageable.builder()
                .responseEntidadList(responseEntidadList)
                .pageNumber(entidadPageableList.getNumber())
                .pageSize(entidadPageableList.getSize())
                .totalPages(entidadPageableList.getTotalPages())
                .totalElements(entidadPageableList.getTotalElements())
                .end(entidadPageableList.isLast())
                .build();
        ResponseApi<ResponseEntidadListPageable> responseApi = createGenericResponseApi(responseEntidadListPageable,path, Constants.STATUS_OK);
        log.info("RESPONSE ENTIDAD: {}", responseApi);
        return responseApi;
    }

    private void validateRazonSocial(String nroDocumento, String razonSocial, TipoDocumentoEntity tipoDocumentoEntity) {
        if(tipoDocumentoEntity.getNombre().equalsIgnoreCase(Constants.TIPO_DOCUMENTO_RUC)){
            validateRUC(nroDocumento,razonSocial);
            }
        if(tipoDocumentoEntity.getNombre().equalsIgnoreCase(Constants.TIPO_DOCUMENTO_DNI)){
            validateDNI(nroDocumento, razonSocial);
        }
    }

    private void validateRUC(String nroDocumento, String razonSocial) {
        ResponseSunat responseSunat = getExecutionSunat(nroDocumento);
        if (!responseSunat.getRazonSocial().equalsIgnoreCase(razonSocial)) {
            log.warn("Razon social de la entidad y de la sunat no son iguales");
            throw new SintadAppNotAcceptableException("Razon social de la entidad y de la sunat no son iguales");
        }
    }

    private void validateDNI(String nroDocumento, String razonSocial) {
        ResponseReniec responseReniec = getExecutionReniec(nroDocumento);
        String fullName  = responseReniec.getNombres() + " " + responseReniec.getApellidoPaterno() + " " + responseReniec.getApellidoMaterno();
        if (!fullName.equalsIgnoreCase(razonSocial)) {
            log.warn("Nombres de la entidad y de la reniec no son iguales");
            throw new SintadAppNotAcceptableException("Nombres de la entidad y de la reniec no son iguales");
        }
    }

    private EntidadEntity createEntidadEntity(RequestSaveEntidad entidad, TipoDocumentoEntity tipoDocumentoEntity, TipoContribuyenteEntity tipoContribuyenteEntity) {
        return EntidadEntity.builder()
                .tipoDocumento(tipoDocumentoEntity)
                .nroDocumento(entidad.getNroDocumento())
                .razonSocial(entidad.getRazonSocial())
                .nombreComercial(entidad.getNombreComercial())
                .tipoContribuyente(tipoContribuyenteEntity)
                .direccion(entidad.getDireccion())
                .telefono(entidad.getTelefono())
                .estado(Constants.STATUS_ACTIVE)
                .build();
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

    private ResponseEntidad getEntidad(EntidadEntity entidadEntity) {
        TipoDocumentoResponse tipoDocumentoResponse = createTipoDocumentoResponse(entidadEntity);
        ResponseEntidad responseEntidad = createResponseEntidad(entidadEntity, tipoDocumentoResponse);
        log.info("ENTIDAD: {}", responseEntidad);
        return responseEntidad;
    }

    private TipoDocumentoResponse createTipoDocumentoResponse(EntidadEntity entidadEntity) {
        return TipoDocumentoResponse.builder()
                .codigo(entidadEntity.getTipoDocumento().getCodigo())
                .nombre(entidadEntity.getTipoDocumento().getNombre())
                .descripcion(entidadEntity.getTipoDocumento().getDescripcion())
                .build();
    }

    private ResponseEntidad createResponseEntidad(EntidadEntity entidadEntity, TipoDocumentoResponse tipoDocumentoResponse) {
        return ResponseEntidad.builder()
                .id(entidadEntity.getId())
                .nroDocumento(entidadEntity.getNroDocumento())
                .razonSocial(entidadEntity.getRazonSocial())
                .nombreComercial(entidadEntity.getNombreComercial())
                .direccion(entidadEntity.getDireccion())
                .telefono(entidadEntity.getTelefono())
                .tipoContribuyente(entidadEntity.getTipoContribuyente().getNombre())
                .tipoDocumentoResponse(tipoDocumentoResponse)
                .build();
    }
    public ResponseSunat getExecutionSunat(String numero){
        String authorization = "Bearer "+tokenApi;
        ResponseSunat responseSunat = sunat.getInfoSunat(numero,authorization);
        log.info("RESPONSE SUNAT: {}", responseSunat);
        return responseSunat;
    }
    public ResponseReniec getExecutionReniec(String numero){
        String authorization = "Bearer "+tokenApi;
        ResponseReniec responseReniec = reniec.getInfoReniec(numero,authorization);
        log.info("RESPONSE RENIEC: {}", responseReniec);
        return  responseReniec;
    }
}
