package com.prueba.sintad.repository;

import com.prueba.sintad.entity.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Integer> {
    //ELIMINADO LOGICO
    @Modifying
    @Query("UPDATE TipoDocumentoEntity t SET "+
            "t.estado = false " +
            "WHERE t.id = :id")
    TipoDocumentoEntity deleteTipoDocumentoById(Integer id);


    @Modifying
    @Query("UPDATE TipoDocumentoEntity t SET " +
            "t.codigo = :codigo, " +
            "t.nombre = :nombre, " +
            "t.descripcion = :descripcion " +
            "WHERE t.id = :id")
    TipoDocumentoEntity updateById(Integer id,
                      String codigo,
                      String nombre,
                      String descripcion);
}
