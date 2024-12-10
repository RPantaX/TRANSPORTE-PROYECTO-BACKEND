package com.prueba.sintad.repository;

import com.prueba.sintad.entity.TaxpayerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxpayerTypeRepository extends JpaRepository<TaxpayerTypeEntity, Integer> {

    @Modifying
    @Query("UPDATE TaxpayerTypeEntity t SET "+
            "t.state = false " +
            "WHERE t.id = :id")
    TaxpayerTypeEntity deleteTaxpayerTypeById(@Param("id") Integer id);

    //ACTUALIZAR
    @Modifying
    @Query("UPDATE TaxpayerTypeEntity t SET " +
            "t.name = :nombre " +
            "WHERE t.id = :id")
    TaxpayerTypeEntity updateById(@Param("id") Integer id,
                      @Param("nombre") String nombre);

    boolean existsByName(String name);
}
