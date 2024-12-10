package com.prueba.sintad.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_tipo_documento")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_documento")
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 20)
    private String code;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "descripcion", length = 200)
    private String description;

    @Column(name = "estado", nullable = false)
    private Boolean state;
}
