package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotAcceptableException;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotFoundException;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.request.RequestUpdateEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.ResponseEntidad;
import com.prueba.sintad.aggregates.response.ResponseEntidadListPageable;
import com.prueba.sintad.aggregates.response.rest.ResponseReniec;
import com.prueba.sintad.aggregates.response.rest.ResponseSunat;
import com.prueba.sintad.entity.EntidadEntity;
import com.prueba.sintad.entity.DocumentTypeEntity;
import com.prueba.sintad.entity.TaxpayerTypeEntity;
import com.prueba.sintad.mapper.EntidadMapper;
import com.prueba.sintad.repository.EntidadRepository;
import com.prueba.sintad.repository.TaxpayerTypeRepository;
import com.prueba.sintad.repository.DocumentTypeRepository;
import com.prueba.sintad.rest.client.ReniecClient;
import com.prueba.sintad.rest.client.SunatClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntidadAdapterTest {
    @Mock
    private EntidadRepository entidadRepository;
    @Mock
    private TaxpayerTypeRepository taxpayerTypeRepository;
    @Mock
    private DocumentTypeRepository documentTypeRepository;
    @Mock
    private EntidadMapper entidadMapper;
    @Mock
    private ReniecClient reniecClient;
    @Mock
    private SunatClient sunatClient;

    @InjectMocks
    private EntidadAdapter entidadAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void saveEntidadNormalOutSuccess(){
        //Mock para requestSavedEntidad
        RequestSaveEntidad request = getSaveEntidadRequest();
        //Tipos de documentos esperados
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("PASAPORTE");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "NATURAL", true);
        EntidadEntity entidadEntitySaved = getEntidadEntity();
        EntidadDTO entidadDTOEsperada = getEntidadDTO();
        //comportamiento
        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(documentTypeEntity));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.of(taxpayerTypeEntity));
        when(entidadRepository.save(any(EntidadEntity.class))).thenReturn(entidadEntitySaved);
        when(entidadMapper.convertToDto(entidadEntitySaved)).thenReturn(entidadDTOEsperada);

        // Execute
        ResponseApi<ResponseEntidad> response = entidadAdapter.saveEntidadOut(request);

        // Verify
        //verificamos los mensajes y estados.
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
        //verificamos el cuerpo
        assertEquals(entidadDTOEsperada.getId(), response.getData().getId());
        assertEquals(entidadDTOEsperada.getDocumentNumber(), response.getData().getDocumentNumber());
        assertEquals(entidadDTOEsperada.getLegalName(), response.getData().getLegalName());
        assertEquals(entidadDTOEsperada.getCommercialName(), response.getData().getCommercialName());
        assertEquals(entidadDTOEsperada.getAddress(), response.getData().getAddress());
        assertEquals(entidadDTOEsperada.getPhone(), response.getData().getPhone());
        verify(entidadRepository, times(1)).save(any(EntidadEntity.class));
    }
    @Test
    void saveEntidadDNIOutSuccess(){
        //Mock para requestSavedEntidad
        RequestSaveEntidad request = getSaveEntidadRequest();
        //Tipos de documentos esperados
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("DNI");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "NATURAL", true);
        EntidadEntity entidadEntitySaved = getEntidadEntity();
        ResponseReniec infoReniec = getResponseReniec();
        EntidadDTO entidadDTOEsperada = getEntidadDTO();
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        //comportamiento
        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(documentTypeEntity));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.of(taxpayerTypeEntity));
        when(reniecClient.getInfoReniec(anyString(), anyString())).thenReturn(infoReniec);
        when(entidadRepository.save(any(EntidadEntity.class))).thenReturn(entidadEntitySaved);
        when(entidadMapper.convertToDto(entidadEntitySaved)).thenReturn(entidadDTOEsperada);

        // Execute
        ResponseApi<ResponseEntidad> response = entidadAdapter.saveEntidadOut(request);

        // Verify
        //verificamos los mensajes y estados.
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
        //verificamos el cuerpo
        assertEquals(entidadDTOEsperada.getId(), response.getData().getId());
        assertEquals(entidadDTOEsperada.getDocumentNumber(), response.getData().getDocumentNumber());
        assertEquals(entidadDTOEsperada.getLegalName(), response.getData().getLegalName());
        assertEquals(entidadDTOEsperada.getCommercialName(), response.getData().getCommercialName());
        assertEquals(entidadDTOEsperada.getAddress(), response.getData().getAddress());
        assertEquals(entidadDTOEsperada.getPhone(), response.getData().getPhone());
        verify(entidadRepository, times(1)).save(any(EntidadEntity.class));
    }

    @Test
    void saveEntidadRUCOutSuccess(){
        //Mock para requestSavedEntidad
        RequestSaveEntidad request = getSaveEntidadRequest();
        //Tipos de documentos esperados
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("RUC");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);
        EntidadEntity entidadEntitySaved = getEntidadEntity();
        ResponseSunat infoSUNAT = getResponseSUNAT();
        EntidadDTO entidadDTOEsperada = getEntidadDTO();
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        //comportamiento
        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(documentTypeEntity));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.of(taxpayerTypeEntity));
        when(sunatClient.getInfoSunat(anyString(), anyString())).thenReturn(infoSUNAT);
        when(entidadRepository.save(any(EntidadEntity.class))).thenReturn(entidadEntitySaved);
        when(entidadMapper.convertToDto(entidadEntitySaved)).thenReturn(entidadDTOEsperada);

        // Execute
        ResponseApi<ResponseEntidad> response = entidadAdapter.saveEntidadOut(request);

        // Verify
        //verificamos los mensajes y estados.
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
        //verificamos el cuerpo
        assertEquals(entidadDTOEsperada.getId(), response.getData().getId());
        assertEquals(entidadDTOEsperada.getDocumentNumber(), response.getData().getDocumentNumber());
        assertEquals(entidadDTOEsperada.getLegalName(), response.getData().getLegalName());
        assertEquals(entidadDTOEsperada.getCommercialName(), response.getData().getCommercialName());
        assertEquals(entidadDTOEsperada.getAddress(), response.getData().getAddress());
        assertEquals(entidadDTOEsperada.getPhone(), response.getData().getPhone());
        verify(entidadRepository, times(1)).save(any(EntidadEntity.class));
    }

    @Test
    void testSaveEntidadOut_DuplicateNroDocumento_ThrowsException() {
        // Mock inputs
        RequestSaveEntidad request = getSaveEntidadRequest();

        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(true);

        // Execute & Verify
        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.saveEntidadOut(request));
        assertEquals("Entidad ya existe", exception.getMessage());
        verify(entidadRepository, never()).save(any(EntidadEntity.class));
    }

    @Test
    void testSaveEntidadOut_TipoDocumentoNotFound_ThrowsException() {
        // Mock inputs
        RequestSaveEntidad request = getSaveEntidadRequest();

        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.empty());

        // Execute & Verify
        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.saveEntidadOut(request));
        assertEquals("Tipo de documento no existe", exception.getMessage());
        verify(entidadRepository, never()).save(any(EntidadEntity.class));
    }

    @Test
    void testSaveEntidadOut_TipoContribuyenteNotFound_ThrowsException() {
        // Mock inputs
        RequestSaveEntidad request = getSaveEntidadRequest();

        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(getTipoDocumento("DNI")));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.empty());

        // Execute & Verify
        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.saveEntidadOut(request));
        assertEquals("Tipo de contribuyente no existe", exception.getMessage());
        verify(entidadRepository, never()).save(any(EntidadEntity.class));
    }
    @Test
    void testSaveEntidadOut_ReniecClientThrowsException_ThrowsException() {
        // Mock inputs
        RequestSaveEntidad request = getSaveEntidadRequest();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("DNI");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "NATURAL", true);

        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(documentTypeEntity));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.of(taxpayerTypeEntity));
        when(reniecClient.getInfoReniec(anyString(), anyString())).thenThrow(RuntimeException.class);

        // Execute & Verify
        assertThrows(RuntimeException.class, () -> entidadAdapter.saveEntidadOut(request));
        verify(entidadRepository, never()).save(any(EntidadEntity.class));
    }
    @Test
    void testSaveEntidadOut_SunatClientThrowsException_ThrowsException() {
        // Mock inputs
        RequestSaveEntidad request = getSaveEntidadRequest();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("RUC");
        TaxpayerTypeEntity tipoContribuyenteEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);

        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(documentTypeEntity));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.of(tipoContribuyenteEntity));
        when(sunatClient.getInfoSunat(anyString(), anyString())).thenThrow(RuntimeException.class);

        // Execute & Verify
        assertThrows(RuntimeException.class, () -> entidadAdapter.saveEntidadOut(request));
        verify(entidadRepository, never()).save(any(EntidadEntity.class));
    }

    @Test
    void testSaveEntidadOut_RazonSocialIsNotEqualsToReniec_ThrowsException() {
        // Mock inputs
        RequestSaveEntidad request = getSaveEntidadRequest();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("DNI");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "NATURAL", true);
        ResponseReniec infoReniec = getResponseReniec();
        infoReniec.setNombres("Juan Carlos");
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        //comportamiento
        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(documentTypeEntity));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.of(taxpayerTypeEntity));
        when(reniecClient.getInfoReniec(anyString(), anyString())).thenReturn(infoReniec);

        // Execute & Verify
        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.saveEntidadOut(request));
        assertEquals("La razón social no coincide con la información de RENIEC", exception.getMessage());
        verify(entidadRepository, never()).save(any(EntidadEntity.class));
    }

    @Test
    void testSaveEntidadOut_RazonSocialIsNotEqualsToSunat_ThrowsException() {
        // Mock inputs
        RequestSaveEntidad request = getSaveEntidadRequest();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("RUC");
        TaxpayerTypeEntity tipoContribuyenteEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);
        ResponseSunat infoSunat = getResponseSUNAT();
        infoSunat.setRazonSocial("Juan Carlos");
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        //comportamiento
        when(entidadRepository.existsByDocumentNumber(request.getDocumentNumber())).thenReturn(false);
        when(documentTypeRepository.findById(request.getDocumentTypeId())).thenReturn(Optional.of(documentTypeEntity));
        when(taxpayerTypeRepository.findById(request.getTaxpayerTypeId())).thenReturn(Optional.of(tipoContribuyenteEntity));
        when(sunatClient.getInfoSunat(anyString(), anyString())).thenReturn(infoSunat);

        // Execute & Verify
        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.saveEntidadOut(request));
        assertEquals("La razón social no coincide con la información de SUNAT", exception.getMessage());
        verify(entidadRepository, never()).save(any(EntidadEntity.class));
    }

    @Test
    void testFindEntidadByIdOut_Success() {
        Integer id = 1;
        EntidadEntity mockEntidad = new EntidadEntity();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("RUC");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);
        mockEntidad.setId(id);
        mockEntidad.setDocumentTypeEntity(documentTypeEntity);
        mockEntidad.setTaxpayerTypeEntity(taxpayerTypeEntity);
        Optional<EntidadEntity> entidad = Optional.of(mockEntidad);

        when(entidadRepository.findEntidadById(id)).thenReturn(entidad);
        ResponseApi<ResponseEntidad> response = entidadAdapter.findEntidadByIdOut(id);

        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getStatus());
        assertEquals(entidad.get().getId(), response.getData().getId());
        verify(entidadRepository, times(1)).findEntidadById(id);
    }

    @Test
    void testFindEntidadByIdOut_NotFound() {
        Integer id = 1;
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SintadAppNotFoundException.class, () -> {
            entidadAdapter.findEntidadByIdOut(id);
        });
        assertEquals("Entidad no encontrada", exception.getMessage());
    }

    @Test
    void testUpdateEntidadOut_Success() {
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("OTROS");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);
        entidadEntity.setDocumentTypeEntity(documentTypeEntity);
        entidadEntity.setTaxpayerTypeEntity(taxpayerTypeEntity);
        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(entidadRepository.updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);

        // Execute
        ResponseApi<String > response = entidadAdapter.updateEntidadOut(entidadEntityUpdated, id);

        // Verify
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    void testUpdateEntidadOut_DNI_Success(){
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("DNI");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "NATURAL", true);
        entidadEntity.setDocumentTypeEntity(documentTypeEntity);
        entidadEntity.setTaxpayerTypeEntity(taxpayerTypeEntity);
        ResponseReniec infoReniec = getResponseReniec();
        infoReniec.setNombres("Jefferson Alessandro");
        infoReniec.setApellidoPaterno("Panta");
        infoReniec.setApellidoMaterno("Ruiz");
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(reniecClient.getInfoReniec(anyString(), anyString())).thenReturn(infoReniec);
        when(entidadRepository.updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);

        // Execute
        ResponseApi<String > response = entidadAdapter.updateEntidadOut(entidadEntityUpdated, id);

        // Verify
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    void testUpdateEntidadOut_RUC_Success(){
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("RUC");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);
        entidadEntity.setDocumentTypeEntity(documentTypeEntity);
        entidadEntity.setTaxpayerTypeEntity(taxpayerTypeEntity);
        ResponseSunat infoSunat = getResponseSUNAT();
        infoSunat.setRazonSocial("Jefferson Alessandro Panta Ruiz");
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(sunatClient.getInfoSunat(anyString(), anyString())).thenReturn(infoSunat);
        when(entidadRepository.updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(1);

        // Execute
        ResponseApi<String > response = entidadAdapter.updateEntidadOut(entidadEntityUpdated, id);

        // Verify
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    void testUpdateEntidadOut_DifferentsIds(){
        // Mock inputs
        Integer id = 2;
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();

        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.empty());

        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.updateEntidadOut(entidadEntityUpdated, id));
        assertEquals("El id del path con el id de la entidad no coincide", exception.getMessage());
        verify(entidadRepository, never()).updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateEntidadOut_EntidadNotFound(){
        // Mock inputs
        Integer id = 1;
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();

        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.empty());

        Exception exception= assertThrows(SintadAppNotFoundException.class, () -> entidadAdapter.updateEntidadOut(entidadEntityUpdated, id));
        assertEquals("Entidad no encontrada", exception.getMessage());
        verify(entidadRepository, never()).updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateEntidadOut_NroDocumentoAlreadyExists(){
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        entidadEntityUpdated.setDocumentNumber("553456789");
        EntidadEntity entidadEntityDuplicated = getEntidadEntity();
        entidadEntityDuplicated.setId(2);
        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(entidadRepository.existsByDocumentNumber(entidadEntityUpdated.getDocumentNumber())).thenReturn(true);

        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.updateEntidadOut(entidadEntityUpdated, id));
        assertEquals("La Entidad ya existe", exception.getMessage());
        verify(entidadRepository, never()).updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
    }
    @Test
    void testUpdateEntidadOut_RazonSocialIsNotEqualsToReniec_ThrowsException(){
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("DNI");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "NATURAL", true);
        entidadEntity.setDocumentTypeEntity(documentTypeEntity);
        entidadEntity.setTaxpayerTypeEntity(taxpayerTypeEntity);
        ResponseReniec infoReniec = getResponseReniec();
        infoReniec.setNombres("Juan Carlos");
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(reniecClient.getInfoReniec(anyString(), anyString())).thenReturn(infoReniec);

        // Execute & Verify
        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.updateEntidadOut(entidadEntityUpdated, id));
        assertEquals("La razón social no coincide con la información de RENIEC", exception.getMessage());
        verify(entidadRepository, never()).updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateEntidadOut_RazonSocialIsNotEqualsToSunat_ThrowsException(){
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("RUC");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);
        entidadEntity.setDocumentTypeEntity(documentTypeEntity);
        entidadEntity.setTaxpayerTypeEntity(taxpayerTypeEntity);
        ResponseSunat infoSunat = getResponseSUNAT();
        infoSunat.setRazonSocial("Juan Carlos");
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(sunatClient.getInfoSunat(anyString(), anyString())).thenReturn(infoSunat);

        // Execute & Verify
        Exception exception= assertThrows(SintadAppNotAcceptableException.class, () -> entidadAdapter.updateEntidadOut(entidadEntityUpdated, id));
        assertEquals("La razón social no coincide con la información de SUNAT", exception.getMessage());
        verify(entidadRepository, never()).updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
    }
    @Test
    void testUpdateEntidadOut_ReniecClientThrowsException_ThrowsException(){
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("DNI");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "NATURAL", true);
        entidadEntity.setDocumentTypeEntity(documentTypeEntity);
        entidadEntity.setTaxpayerTypeEntity(taxpayerTypeEntity);
        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(reniecClient.getInfoReniec(anyString(), anyString())).thenThrow(RuntimeException.class);

        // Execute & Verify
        assertThrows(RuntimeException.class, () -> entidadAdapter.updateEntidadOut(entidadEntityUpdated, id));
        verify(entidadRepository, never()).updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
    }
    @Test
    void testUpdateEntidadOut_SunatClientThrowsException_ThrowsException(){
        // Mock inputs
        Integer id = 1;
        EntidadEntity entidadEntity = getEntidadEntity();
        RequestUpdateEntidad entidadEntityUpdated = getEntidadToUpdate();
        DocumentTypeEntity documentTypeEntity = getTipoDocumento("RUC");
        TaxpayerTypeEntity taxpayerTypeEntity = new TaxpayerTypeEntity(1, "JURIDICA", true);
        entidadEntity.setDocumentTypeEntity(documentTypeEntity);
        entidadEntity.setTaxpayerTypeEntity(taxpayerTypeEntity);
        // Mock repository
        when(entidadRepository.findEntidadById(id)).thenReturn(Optional.of(entidadEntity));
        when(sunatClient.getInfoSunat(anyString(), anyString())).thenThrow(RuntimeException.class);

        // Execute & Verify
        assertThrows(RuntimeException.class, () -> entidadAdapter.updateEntidadOut(entidadEntityUpdated, id));
        verify(entidadRepository, never()).updateEntidadById(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString());
    }
    @Test
    void testDeleteEntidadOut_Success() {
        // Mock inputs
        Integer id = 1;

        // Mock repository
        when(entidadRepository.existsById(id)).thenReturn(true);
        when(entidadRepository.deleteEntidadById(id)).thenReturn(1);

        // Execute
        ResponseApi<String> response = entidadAdapter.deleteEntidadOut(id);

        // Verify
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
    }

    @Test
    void testDeleteEntidadOut_NotFound() {
        // Mock inputs
        Integer id = 1;

        // Mock repository
        when(entidadRepository.existsById(id)).thenReturn(false);

        // Execute
        Exception exception = assertThrows(SintadAppNotFoundException.class, () -> entidadAdapter.deleteEntidadOut(id));
        assertEquals("Entidad no encontrada", exception.getMessage());
    }
    @Test
    void testFindAllEntidadSuccessEmpty(){
        int pageNumber = Integer.parseInt(Constants.NUM_PAG_BY_DEFECT);
        int pageSize = Integer.parseInt(Constants.SIZE_PAG_BY_DEFECT);
        String orderBy = Constants.ORDER_BY_DEFECT_ALL ;
        String sortDir = Constants.ORDER_DIRECT_BY_DEFECT;
        // Mock inputs
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(orderBy).ascending() :
                Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<EntidadEntity> entidadEntities = Page.empty();
        // Mock repository
        when(entidadRepository.findAllPageableByEstado(true, pageable)).thenReturn(entidadEntities);

        // Execute
        ResponseApi<ResponseEntidadListPageable> response = entidadAdapter.findAllEntidadOut(pageNumber, pageSize, orderBy, sortDir);

        // Verify
        assertNotNull(response);
        assertEquals(Constants.STATUS_OK, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(0, response.getData().getPageSize());
        assertEquals(0, response.getData().getPageNumber());
        assertEquals(0, response.getData().getTotalElements());
        assertEquals(1, response.getData().getTotalPages());
        assertTrue(response.getData().isEnd());
    }

    private RequestUpdateEntidad getEntidadToUpdate() {
        return RequestUpdateEntidad.builder()
                .id(1)
                .documentNumber("123456789")
                .legalName("Jefferson Alessandro Panta Ruiz")
                .commercialName("COMERCIAL UPDATED")
                .address("AV. UPDATED")
                .phone("123456789")
                .build();
    }

    private ResponseSunat getResponseSUNAT() {
        return ResponseSunat.builder()
                .razonSocial("Juan Perez Lopez")
                .build();
    }

    private ResponseReniec getResponseReniec() {
        return new ResponseReniec("Juan", "Perez", "Lopez", "DNI","720320083","2");
    }

    private RequestSaveEntidad getSaveEntidadRequest() {
        return RequestSaveEntidad.builder()
                .documentNumber("123456789")
                .legalName("Juan Perez Lopez")
                .commercialName("COMERCIAL")
                .address("AV. PRINCIPAL 123")
                .phone("987654321")
                .documentTypeId(1)
                .taxpayerTypeId(1)
                .build();
    }

    private DocumentTypeEntity getTipoDocumento(String nombre) {
        return new DocumentTypeEntity(1, "01", nombre, "XXXXXXXXXXXX", true);
    }

    private EntidadEntity getEntidadEntity() {
        return EntidadEntity.builder()
                .documentNumber("123456789")
                .legalName("Juan Perez Lopez")
                .commercialName("COMERCIAL")
                .address("AV. PRINCIPAL 123")
                .phone("987654321")
                .build();
    }

    private EntidadDTO getEntidadDTO() {
        return EntidadDTO.builder()
                .documentNumber("123456789")
                .legalName("JUAN PEREZ LOPEZ")
                .commercialName("COMERCIAL")
                .address("AV. PRINCIPAL 123")
                .phone("987654321")
                .build();
    }

}