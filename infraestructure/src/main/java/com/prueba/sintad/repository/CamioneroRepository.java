package com.prueba.sintad.repository;

import com.prueba.sintad.entity.CamioneroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CamioneroRepository extends JpaRepository<CamioneroEntity, Integer> {
    boolean existsByDni(String dni);
    boolean existsByNroLicencia(String nroLicencia);
    @Query(
            "SELECT e FROM CamioneroEntity e " +
                    "LEFT JOIN FETCH e.entidad td " +
                    "LEFT JOIN FETCH e.camion tc "+
                    "WHERE e.estado = :estado "
    )
    Page<CamioneroEntity> findAllPageableByEstado(@Param("estado") Boolean estado, Pageable pageable);
}
