package com.prueba.sintad.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity //Indica que la clase es una entidad
@Table(name = "tb_camionero") //Nombre de la tabla
@Setter
@Getter
@AllArgsConstructor //constructor con todos los parametros
@NoArgsConstructor //constructor vacio
@Builder //Patron de diseño creacional
public class CamioneroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camionero")
    private Integer id;
    @Column
    private String dni;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private String direccion;
    @Column
    private String telefono;
    @Column
    private int edad;
    @ManyToOne
    @JoinColumn(name = "id_entidad", nullable = false)
    private EntidadEntity entidad;

    @OneToOne
    @JoinColumn(name = "id_camion", nullable = false)
    private CamionEntity camion;

    @Column(name = "nro_licencia", nullable = false, unique = true, length = 20)
    private String nroLicencia;

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
