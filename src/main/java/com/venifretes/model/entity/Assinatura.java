package com.venifretes.model.entity;

import com.venifretes.model.enums.MetodoPagamento;
import com.venifretes.model.enums.PlanoTipo;
import com.venifretes.model.enums.StatusAssinatura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "assinaturas", indexes = {
    @Index(name = "idx_assinatura_freteiro", columnList = "freteiro_id"),
    @Index(name = "idx_assinatura_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freteiro_id", nullable = false)
    private Freteiro freteiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "plano_id", nullable = false)
    private PlanoTipo planoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plano_id", insertable = false, updatable = false)
    private Plano plano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusAssinatura status;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "valor_mensal", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMensal;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", length = 20)
    private MetodoPagamento metodoPagamento;

    @Column(name = "pagamento_recorrente")
    private Boolean pagamentoRecorrente = false;

    @Column(name = "id_assinatura_pagbank")
    private String idAssinaturaPagBank;

    @Column(name = "em_periodo_teste")
    private Boolean emPeriodoTeste = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "cancelada_em")
    private LocalDateTime canceladaEm;

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;
}
