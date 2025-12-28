package com.venifretes.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pontos_ranking",
    uniqueConstraints = @UniqueConstraint(columnNames = {"freteiro_id", "cidade"}),
    indexes = {
        @Index(name = "idx_pontos_freteiro", columnList = "freteiro_id"),
        @Index(name = "idx_pontos_cidade", columnList = "cidade"),
        @Index(name = "idx_pontos_ativos", columnList = "pontos_ativos")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PontosRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freteiro_id", nullable = false)
    private Freteiro freteiro;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(name = "pontos_ativos", nullable = false)
    @Builder.Default
    private Integer pontosAtivos = 0;

    @Column(name = "pontos_gastos")
    @Builder.Default
    private Integer pontosGastos = 0;

    @Column(name = "total_pontos_comprados")
    @Builder.Default
    private Integer totalPontosComprados = 0;

    @Column(name = "expira_em")
    private LocalDateTime expiraEm;

    @Column(name = "ultima_compra")
    private LocalDateTime ultimaCompra;

    @Column(name = "ativo")
    @Builder.Default
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper methods
    public void adicionarPontos(Integer quantidade, Integer diasValidade) {
        this.pontosAtivos += quantidade;
        this.totalPontosComprados += quantidade;
        this.ultimaCompra = LocalDateTime.now();
        this.expiraEm = LocalDateTime.now().plusDays(diasValidade);
    }

    public void removerPontos(Integer quantidade) {
        this.pontosAtivos = Math.max(0, this.pontosAtivos - quantidade);
        this.pontosGastos += quantidade;
    }

    public boolean isExpirado() {
        return expiraEm != null && LocalDateTime.now().isAfter(expiraEm);
    }

    public void expirarPontos() {
        if (isExpirado()) {
            this.pontosGastos += this.pontosAtivos;
            this.pontosAtivos = 0;
        }
    }
}
