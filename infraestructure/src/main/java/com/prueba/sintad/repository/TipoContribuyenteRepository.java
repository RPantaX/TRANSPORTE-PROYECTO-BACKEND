package com.prueba.sintad.repository;

import com.prueba.sintad.entity.TipoContribuyenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoContribuyenteRepository extends JpaRepository<TipoContribuyenteEntity, Integer> {

    @Modifying
    @Query("UPDATE TipoContribuyenteEntity t SET "+
            "t.estado = false " +
            "WHERE t.id = :id")
    TipoContribuyenteEntity deleteTipoContribuyenteById(@Param("id") Integer id);

    //ACTUALIZAR
    @Modifying
    @Query("UPDATE TipoContribuyenteEntity t SET " +
            "t.nombre = :nombre " +
            "WHERE t.id = :id")
    TipoContribuyenteEntity updateById(@Param("id") Integer id,
                      @Param("nombre") String nombre);
}
