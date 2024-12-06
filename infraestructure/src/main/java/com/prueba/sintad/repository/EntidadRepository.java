package com.prueba.sintad.repository;

import com.prueba.sintad.entity.EntidadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadRepository extends JpaRepository<EntidadEntity, Integer> {

    @Query(
        "SELECT e FROM EntidadEntity e " +
        "LEFT JOIN FETCH e.tipoDocumento td " +
        "LEFT JOIN FETCH e.tipoContribuyente tc "+
        "WHERE e.estado = :estado"
        )
    Page<EntidadEntity> findAllPageableByEstado(@Param("estado") Boolean estado, Pageable pageable);

    //ELIMINADO LOGICO
    @Modifying
    @Query("UPDATE EntidadEntity e SET e.estado = false WHERE e.id = :id")
    void deleteEntidadById(@Param("id") Integer id);

    @Modifying
    @Query("UPDATE EntidadEntity e SET " +
            "e.nroDocumento = :nroDocumento, " +
            "e.razonSocial = :razonSocial, " +
            "e.nombreComercial = :nombreComercial, " +
            "e.direccion = :direccion, " +
            "e.telefono = :telefono " +
            "WHERE e.id = :id")
    int updateById(@Param("id") Integer id,
                      @Param("nroDocumento") String nroDocumento,
                      @Param("razonSocial") String razonSocial,
                      @Param("nombreComercial") String nombreComercial,
                      @Param("direccion") String direccion,
                      @Param("telefono") String telefono);


}
