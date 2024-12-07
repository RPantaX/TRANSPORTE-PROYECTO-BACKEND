package com.prueba.sintad.adapters;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.dto.EntidadDTO;
import com.prueba.sintad.aggregates.request.RequestSaveEntidad;
import com.prueba.sintad.aggregates.response.ResponseApi;
import com.prueba.sintad.aggregates.response.rest.ResponseReniec;
import com.prueba.sintad.aggregates.response.rest.ResponseSunat;
import com.prueba.sintad.entity.EntidadEntity;
import com.prueba.sintad.entity.TipoContribuyenteEntity;
import com.prueba.sintad.entity.TipoDocumentoEntity;
import com.prueba.sintad.mapper.EntidadMapper;
import com.prueba.sintad.repository.EntidadRepository;
import com.prueba.sintad.repository.TipoContribuyenteRepository;
import com.prueba.sintad.repository.TipoDocumentoRepository;
import com.prueba.sintad.rest.client.ReniecClient;
import com.prueba.sintad.rest.client.SunatClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntidadAdapterTest {
    @Mock
    private EntidadRepository entidadRepository;
    @Mock
    private TipoContribuyenteRepository tipoContribuyenteRepository;
    @Mock
    private TipoDocumentoRepository tipoDocumentoRepository;
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
        TipoDocumentoEntity tipoDocumentoEntity = getTipoDocumento("PASAPORTE");
        TipoContribuyenteEntity tipoContribuyenteEntity = new TipoContribuyenteEntity(1, "NATURAL", true);
        EntidadEntity entidadEntitySaved = getEntidadEntity();
        EntidadDTO entidadDTOEsperada = getEntidadDTO();
        //comportamiento
        when(entidadRepository.existsByNroDocumento(request.getNroDocumento())).thenReturn(false);
        when(tipoDocumentoRepository.findById(request.getIdTipoDocumento())).thenReturn(Optional.of(tipoDocumentoEntity));
        when(tipoContribuyenteRepository.findById(request.getIdTipoContribuyente())).thenReturn(Optional.of(tipoContribuyenteEntity));
        when(entidadRepository.save(any(EntidadEntity.class))).thenReturn(entidadEntitySaved);
        when(entidadMapper.convertToDto(entidadEntitySaved)).thenReturn(entidadDTOEsperada);

        // Execute
        ResponseApi<EntidadDTO> response = entidadAdapter.saveEntidadOut(request);

        // Verify
        //verificamos los mensajes y estados.
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
        //verificamos el cuerpo
        assertEquals(entidadDTOEsperada.getId(), response.getData().getId());
        assertEquals(entidadDTOEsperada.getNroDocumento(), response.getData().getNroDocumento());
        assertEquals(entidadDTOEsperada.getRazonSocial(), response.getData().getRazonSocial());
        assertEquals(entidadDTOEsperada.getNombreComercial(), response.getData().getNombreComercial());
        assertEquals(entidadDTOEsperada.getDireccion(), response.getData().getDireccion());
        assertEquals(entidadDTOEsperada.getTelefono(), response.getData().getTelefono());
        verify(entidadRepository, times(1)).save(any(EntidadEntity.class));
    }
    @Test
    void saveEntidadDNIOutSuccess(){
        //Mock para requestSavedEntidad
        RequestSaveEntidad request = getSaveEntidadRequest();
        //Tipos de documentos esperados
        TipoDocumentoEntity tipoDocumentoEntity = getTipoDocumento("DNI");
        TipoContribuyenteEntity tipoContribuyenteEntity = new TipoContribuyenteEntity(1, "NATURAL", true);
        EntidadEntity entidadEntitySaved = getEntidadEntity();
        ResponseReniec infoReniec = getResponseReniec();
        EntidadDTO entidadDTOEsperada = getEntidadDTO();
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        //comportamiento
        when(entidadRepository.existsByNroDocumento(request.getNroDocumento())).thenReturn(false);
        when(tipoDocumentoRepository.findById(request.getIdTipoDocumento())).thenReturn(Optional.of(tipoDocumentoEntity));
        when(tipoContribuyenteRepository.findById(request.getIdTipoContribuyente())).thenReturn(Optional.of(tipoContribuyenteEntity));
        when(reniecClient.getInfoReniec(anyString(), anyString())).thenReturn(infoReniec);
        when(entidadRepository.save(any(EntidadEntity.class))).thenReturn(entidadEntitySaved);
        when(entidadMapper.convertToDto(entidadEntitySaved)).thenReturn(entidadDTOEsperada);

        // Execute
        ResponseApi<EntidadDTO> response = entidadAdapter.saveEntidadOut(request);

        // Verify
        //verificamos los mensajes y estados.
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
        //verificamos el cuerpo
        assertEquals(entidadDTOEsperada.getId(), response.getData().getId());
        assertEquals(entidadDTOEsperada.getNroDocumento(), response.getData().getNroDocumento());
        assertEquals(entidadDTOEsperada.getRazonSocial(), response.getData().getRazonSocial());
        assertEquals(entidadDTOEsperada.getNombreComercial(), response.getData().getNombreComercial());
        assertEquals(entidadDTOEsperada.getDireccion(), response.getData().getDireccion());
        assertEquals(entidadDTOEsperada.getTelefono(), response.getData().getTelefono());
        verify(entidadRepository, times(1)).save(any(EntidadEntity.class));
    }

    @Test
    void saveEntidadRUCOutSuccess(){
        //Mock para requestSavedEntidad
        RequestSaveEntidad request = getSaveEntidadRequest();
        //Tipos de documentos esperados
        TipoDocumentoEntity tipoDocumentoEntity = getTipoDocumento("RUC");
        TipoContribuyenteEntity tipoContribuyenteEntity = new TipoContribuyenteEntity(1, "JURIDICA", true);
        EntidadEntity entidadEntitySaved = getEntidadEntity();
        ResponseSunat infoSUNAT = getResponseSUNAT();
        EntidadDTO entidadDTOEsperada = getEntidadDTO();
        //simulamos un token
        ReflectionTestUtils.setField(entidadAdapter,"tokenApi","XXXXXXXX",String.class);

        //comportamiento
        when(entidadRepository.existsByNroDocumento(request.getNroDocumento())).thenReturn(false);
        when(tipoDocumentoRepository.findById(request.getIdTipoDocumento())).thenReturn(Optional.of(tipoDocumentoEntity));
        when(tipoContribuyenteRepository.findById(request.getIdTipoContribuyente())).thenReturn(Optional.of(tipoContribuyenteEntity));
        when(sunatClient.getInfoSunat(anyString(), anyString())).thenReturn(infoSUNAT);
        when(entidadRepository.save(any(EntidadEntity.class))).thenReturn(entidadEntitySaved);
        when(entidadMapper.convertToDto(entidadEntitySaved)).thenReturn(entidadDTOEsperada);

        // Execute
        ResponseApi<EntidadDTO> response = entidadAdapter.saveEntidadOut(request);

        // Verify
        //verificamos los mensajes y estados.
        assertNotNull(response);
        assertEquals(Constants.STATUS_CREATED, response.getStatus());
        assertEquals(Constants.MESSAGE_OK, response.getMessage());
        assertNotNull(response.getData());
        //verificamos el cuerpo
        assertEquals(entidadDTOEsperada.getId(), response.getData().getId());
        assertEquals(entidadDTOEsperada.getNroDocumento(), response.getData().getNroDocumento());
        assertEquals(entidadDTOEsperada.getRazonSocial(), response.getData().getRazonSocial());
        assertEquals(entidadDTOEsperada.getNombreComercial(), response.getData().getNombreComercial());
        assertEquals(entidadDTOEsperada.getDireccion(), response.getData().getDireccion());
        assertEquals(entidadDTOEsperada.getTelefono(), response.getData().getTelefono());
        verify(entidadRepository, times(1)).save(any(EntidadEntity.class));
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
                .nroDocumento("123456789")
                .razonSocial("Juan Perez Lopez")
                .nombreComercial("COMERCIAL")
                .direccion("AV. PRINCIPAL 123")
                .telefono("987654321")
                .idTipoDocumento(1)
                .idTipoContribuyente(1)
                .build();
    }

    private TipoDocumentoEntity getTipoDocumento(String nombre) {
        return new TipoDocumentoEntity(1, "01", nombre, "XXXXXXXXXXXX", true);
    }

    private EntidadEntity getEntidadEntity() {
        return EntidadEntity.builder()
                .nroDocumento("123456789")
                .razonSocial("Juan Perez Lopez")
                .nombreComercial("COMERCIAL")
                .direccion("AV. PRINCIPAL 123")
                .telefono("987654321")
                .build();
    }

    private EntidadDTO getEntidadDTO() {
        return EntidadDTO.builder()
                .nroDocumento("123456789")
                .razonSocial("Juan Perez Lopez")
                .nombreComercial("COMERCIAL")
                .direccion("AV. PRINCIPAL 123")
                .telefono("987654321")
                .build();
    }

}