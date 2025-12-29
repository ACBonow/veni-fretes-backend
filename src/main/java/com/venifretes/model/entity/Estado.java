package com.venifretes.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estados", indexes = {
    @Index(name = "idx_estados_sigla", columnList = "sigla", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 2)
    private String sigla;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "codigo_ibge", nullable = false, unique = true)
    private Integer codigoIbge;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "estado", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Cidade> cidades = new ArrayList<>();
}
