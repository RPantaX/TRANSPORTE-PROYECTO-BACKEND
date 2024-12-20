package com.prueba.sintad.repository;

import com.prueba.sintad.entity.CamionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamionRepository extends JpaRepository<CamionEntity, Integer> {
    boolean existsByPlaca(String placa);
}
