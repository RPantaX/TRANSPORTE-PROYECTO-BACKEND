package com.prueba.sintad.repository;

import com.prueba.sintad.entity.DocumentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity, Integer> {
    //ELIMINADO LOGICO
    @Modifying
    @Query("UPDATE DocumentTypeEntity t SET "+
            "t.state = false " +
            "WHERE t.id = :id")
    DocumentTypeEntity deleteDocumentTypeById(Integer id);


    @Modifying
    @Query("UPDATE DocumentTypeEntity t SET " +
            "t.code = :codigo, " +
            "t.name = :nombre, " +
            "t.description = :descripcion " +
            "WHERE t.id = :id")
    DocumentTypeEntity updateById(Integer id,
                                  String codigo,
                                  String nombre,
                                  String descripcion);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM DocumentTypeEntity t WHERE t.code = :code OR t.name = :name")
    boolean existsByCodeOrName(String code, String name);
}
