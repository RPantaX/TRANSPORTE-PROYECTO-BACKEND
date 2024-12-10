package com.prueba.sintad.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_entidad")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entidad")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private DocumentTypeEntity documentTypeEntity;

    @Column(name = "nro_documento", nullable = false, unique = true, length = 25)
    private String documentNumber;

    @Column(name = "razon_social", nullable = false, length = 100)
    private String legalName;

    @Column(name = "nombre_comercial", length = 100)
    private String commercialName;

    @ManyToOne
    @JoinColumn(name = "id_tipo_contribuyente")
    private TaxpayerTypeEntity taxpayerTypeEntity;

    @Column(name = "direccion", length = 250)
    private String address;

    @Column(name = "telefono", length = 50)
    private String phone;

    @Column(name = "estado", nullable = false)
    private Boolean state;
}
