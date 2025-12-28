package com.venifretes.model.entity;

import com.venifretes.model.enums.PlanoTipo;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "planos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plano {

    @Id
    @Enumerated(EnumType.STRING)
    private PlanoTipo id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> features;

    @Column(name = "posicao_ranking", nullable = false)
    private Integer posicaoRanking;

    @Column(name = "limite_fotos", nullable = false)
    private Integer limiteFotos;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false)
    private Boolean ativo = true;
}
