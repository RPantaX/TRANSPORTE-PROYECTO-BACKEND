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
    private TipoDocumentoEntity tipoDocumento;

    @Column(name = "nro_documento", nullable = false, unique = true, length = 25)
    private String nroDocumento;

    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;

    @Column(name = "nombre_comercial", length = 100)
    private String nombreComercial;

    @ManyToOne
    @JoinColumn(name = "id_tipo_contribuyente")
    private TipoContribuyenteEntity tipoContribuyente;

    @Column(name = "direccion", length = 250)
    private String direccion;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
