package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.constants.Paths;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotAcceptableException;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotFoundException;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.request.RequestUpdateEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.ResponseEntidadListPageable;
import com.prueba.sintad.aggregates.response.DocumentTypeResponse;
import com.prueba.sintad.aggregates.response.rest.ResponseReniec;
import com.prueba.sintad.aggregates.response.rest.ResponseSunat;

import com.prueba.sintad.entity.EntidadEntity;
import com.prueba.sintad.entity.TaxpayerTypeEntity;
import com.prueba.sintad.entity.DocumentTypeEntity;

import com.prueba.sintad.repository.EntidadRepository;
import com.prueba.sintad.repository.TaxpayerTypeRepository;
import com.prueba.sintad.repository.DocumentTypeRepository;

import com.prueba.sintad.rest.client.ReniecClient;
import com.prueba.sintad.rest.client.SunatClient;

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
    private final TaxpayerTypeRepository taxpayerTypeRepository;
    private final DocumentTypeRepository documentTypeRepository;
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
    public ResponseApi<ResponseEntidad> saveEntidadOut(RequestSaveEntidad entidad) {
        if(entidadRepository.existsByDocumentNumber(entidad.getDocumentNumber()))
            throw new SintadAppNotAcceptableException("Entidad ya existe");
        DocumentTypeEntity documentTypeEntity = documentTypeRepository.findById(entidad.getDocumentTypeId())
                .orElseThrow( () -> new SintadAppNotAcceptableException("Tipo de documento no existe"));
        TaxpayerTypeEntity taxpayerTypeEntity = taxpayerTypeRepository.findById(entidad.getTaxpayerTypeId())
                .orElseThrow( () -> new SintadAppNotAcceptableException("Tipo de contribuyente no existe"));
        // Convertir campos a mayúsculas
        entidad.setLegalName(entidad.getLegalName().toUpperCase());
        entidad.setAddress(entidad.getAddress().toUpperCase());
        entidad.setCommercialName(entidad.getCommercialName().toUpperCase());

        validateLegalName(entidad.getDocumentNumber(), entidad.getLegalName(), documentTypeEntity);

        EntidadEntity entidadEntity = createEntidadEntity(entidad, documentTypeEntity, taxpayerTypeEntity);
        entidadRepository.save(entidadEntity);
        ResponseEntidad responseEntidad = getEntidad(entidadEntity);
        String path = Paths.Entidad;
        ResponseApi<ResponseEntidad> responseApi = createGenericResponseApi(responseEntidad,path, Constants.STATUS_CREATED);
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
        entidad.setLegalName(entidad.getLegalName().toUpperCase());
        entidad.setAddress(entidad.getAddress().toUpperCase());
        entidad.setCommercialName(entidad.getCommercialName().toUpperCase());

        if(!entidadEntity.getDocumentNumber().equalsIgnoreCase(entidad.getDocumentNumber())){
            //Validar si el nuevo nroDocumento ya existe
            if(entidadRepository.existsByDocumentNumber(entidad.getDocumentNumber()))
                throw new SintadAppNotAcceptableException("La Entidad ya existe");
            validateLegalName(entidad.getDocumentNumber(), entidad.getLegalName(), entidadEntity.getDocumentTypeEntity());
        }
        else{
            //Si el nroDocumento es el mismo no tiene que validar si el nroDocumento ya existe
            validateLegalName(entidad.getDocumentNumber(), entidad.getLegalName(), entidadEntity.getDocumentTypeEntity());
        }
        entidadRepository.updateEntidadById(id, entidad.getDocumentNumber(), entidad.getLegalName(), entidad.getCommercialName(), entidad.getAddress(), entidad.getPhone());
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

    private void validateLegalName(String nroDocumento, String razonSocial, DocumentTypeEntity documentTypeEntity) {
        if(documentTypeEntity.getName().equalsIgnoreCase(Constants.TIPO_DOCUMENTO_RUC)){
            validateRUC(nroDocumento,razonSocial);
            }
        if(documentTypeEntity.getName().equalsIgnoreCase(Constants.TIPO_DOCUMENTO_DNI)){
            validateDNI(nroDocumento, razonSocial);
        }
    }

    private void validateRUC(String nroDocumento, String razonSocial) {
        ResponseSunat responseSunat = getExecutionSunat(nroDocumento);
        if (!responseSunat.getRazonSocial().equalsIgnoreCase(razonSocial)) {
            log.warn("La razón social no coincide con la información de SUNAT");
            throw new SintadAppNotAcceptableException("La razón social no coincide con la información de SUNAT");
        }
    }

    private void validateDNI(String nroDocumento, String razonSocial) {
        ResponseReniec responseReniec = getExecutionReniec(nroDocumento);
        String fullName  = responseReniec.getNombres() + " " + responseReniec.getApellidoPaterno() + " " + responseReniec.getApellidoMaterno();
        if (!fullName.equalsIgnoreCase(razonSocial)) {
            log.warn("La razón social no coincide con la información de RENIEC");
            throw new SintadAppNotAcceptableException("La razón social no coincide con la información de RENIEC");
        }
    }

    private EntidadEntity createEntidadEntity(RequestSaveEntidad entity, DocumentTypeEntity documentTypeEntity, TaxpayerTypeEntity taxpayerTypeEntity) {
        return EntidadEntity.builder()
                .documentTypeEntity(documentTypeEntity)
                .documentNumber(entity.getDocumentNumber())
                .legalName(entity.getLegalName())
                .commercialName(entity.getCommercialName())
                .taxpayerTypeEntity(taxpayerTypeEntity)
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .state(Constants.STATUS_ACTIVE)
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
        DocumentTypeResponse tipoDocumentoResponse = createDocumentTypeResponse(entidadEntity);
        ResponseEntidad responseEntidad = createResponseEntidad(entidadEntity, tipoDocumentoResponse);
        log.info("ENTIDAD: {}", responseEntidad);
        return responseEntidad;
    }

    private DocumentTypeResponse createDocumentTypeResponse(EntidadEntity entidadEntity) {
        return DocumentTypeResponse.builder()
                .code(entidadEntity.getDocumentTypeEntity().getCode())
                .name(entidadEntity.getDocumentTypeEntity().getName())
                .description(entidadEntity.getDocumentTypeEntity().getDescription())
                .build();
    }

    private ResponseEntidad createResponseEntidad(EntidadEntity entidadEntity, DocumentTypeResponse documentTypeResponse) {
        return ResponseEntidad.builder()
                .id(entidadEntity.getId())
                .documentNumber(entidadEntity.getDocumentNumber())
                .legalName(entidadEntity.getLegalName())
                .commercialName(entidadEntity.getCommercialName())
                .address(entidadEntity.getAddress())
                .phone(entidadEntity.getPhone())
                .taxpayerType(entidadEntity.getTaxpayerTypeEntity().getName())
                .documentTypeResponse(documentTypeResponse)
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
