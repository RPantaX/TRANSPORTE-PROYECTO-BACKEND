package com.prueba.sintad.repository;

import com.prueba.sintad.entity.EntidadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EntidadRepository extends JpaRepository<EntidadEntity, Integer> {

    @Query(
        "SELECT e FROM EntidadEntity e " +
        "LEFT JOIN FETCH e.documentTypeEntity td " +
        "LEFT JOIN FETCH e.taxpayerTypeEntity tc "+
        "WHERE e.id = :id "
        )
    Optional<EntidadEntity> findEntidadById(Integer id);

    @Query(
        "SELECT e FROM EntidadEntity e " +
        "LEFT JOIN FETCH e.documentTypeEntity td " +
        "LEFT JOIN FETCH e.taxpayerTypeEntity tc "+
        "WHERE e.state = :state "
         )
    Page<EntidadEntity> findAllPageableByEstado(@Param("state") Boolean state, Pageable pageable);

    //ELIMINADO LOGICO
    @Transactional
    @Modifying
    @Query("UPDATE EntidadEntity e SET e.state = false WHERE e.id = :id")
    int deleteEntidadById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE EntidadEntity e SET " +
            "e.documentNumber = :documentNumber, " +
            "e.legalName = :legalName, " +
            "e.commercialName = :commercialName, " +
            "e.address = :address, " +
            "e.phone = :phone " +
            "WHERE e.id = :id")
    int updateEntidadById(@Param("id") Integer id,
                      @Param("documentNumber") String documentNumber,
                      @Param("legalName") String legalName,
                      @Param("commercialName") String commercialName,
                      @Param("address") String address,
                      @Param("phone") String phone);


    boolean existsByDocumentNumber(String documentNumber);
}
