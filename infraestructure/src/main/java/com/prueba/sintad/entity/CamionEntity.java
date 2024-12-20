package com.prueba.sintad.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tb_camion")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder //patron creacional
public class CamionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camion")
    private Integer id;

    @Column(name = "placa", nullable = false, unique = true, length = 20)
    private String placa;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo;

    @Column(name = "year_fabricacion", nullable = false)
    private Integer yearFabricacion;
    //COLUMNAS DE ABAJO PARA AUDITORIA
    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private Timestamp creadoEn;

    @Column(name = "modificado_en")
    private Timestamp modificadoEn;

    @Column(name = "eliminado_en")
    private Timestamp eliminadoEn;
}
