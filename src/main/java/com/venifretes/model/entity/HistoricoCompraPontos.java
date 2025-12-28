package com.venifretes.model.entity;

import com.venifretes.model.enums.MetodoPagamento;
import com.venifretes.model.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_compra_pontos", indexes = {
    @Index(name = "idx_compra_freteiro", columnList = "freteiro_id"),
    @Index(name = "idx_compra_status", columnList = "status"),
    @Index(name = "idx_compra_transacao", columnList = "transacao_id"),
    @Index(name = "idx_compra_created", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoCompraPontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freteiro_id", nullable = false)
    private Freteiro freteiro;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(name = "quantidade_pontos", nullable = false)
    private Integer quantidadePontos;

    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "desconto_percentual", precision = 5, scale = 2)
    @Builder.Default
    private BigDecimal descontoPercentual = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", length = 20)
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusPagamento status = StatusPagamento.PENDENTE;

    @Column(name = "transacao_id", unique = true, length = 100)
    private String transacaoId;

    @Column(name = "pagbank_charge_id", length = 100)
    private String pagbankChargeId;

    @Column(name = "qr_code_pix", columnDefinition = "TEXT")
    private String qrCodePix;

    @Column(name = "link_pagamento", columnDefinition = "TEXT")
    private String linkPagamento;

    @Column(name = "posicao_estimada_antes")
    private Integer posicaoEstimadaAntes;

    @Column(name = "posicao_estimada_depois")
    private Integer posicaoEstimadaDepois;

    @Column(name = "dias_validade")
    @Builder.Default
    private Integer diasValidade = 30;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper methods
    public void aprovarPagamento() {
        this.status = StatusPagamento.APROVADO;
        this.dataPagamento = LocalDateTime.now();
        this.dataExpiracao = LocalDateTime.now().plusDays(diasValidade);
    }

    public void recusarPagamento(String motivo) {
        this.status = StatusPagamento.RECUSADO;
        this.observacoes = motivo;
    }

    public boolean isPendente() {
        return status == StatusPagamento.PENDENTE || status == StatusPagamento.PROCESSANDO;
    }

    public boolean isAprovado() {
        return status == StatusPagamento.APROVADO;
    }
}
