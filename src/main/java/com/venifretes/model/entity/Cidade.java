package com.venifretes.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cidades",
    indexes = {
        @Index(name = "idx_cidades_estado_id", columnList = "estado_id"),
        @Index(name = "idx_cidades_nome", columnList = "nome"),
        @Index(name = "idx_cidades_codigo_ibge", columnList = "codigo_ibge", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "idx_cidades_estado_nome", columnNames = {"estado_id", "nome"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "codigo_ibge", nullable = false, unique = true)
    private Integer codigoIbge;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
