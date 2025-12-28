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
@Table(name = "avaliacoes", indexes = {
    @Index(name = "idx_avaliacao_freteiro", columnList = "freteiro_id"),
    @Index(name = "idx_avaliacao_usuario", columnList = "usuario_id"),
    @Index(name = "idx_avaliacao_aprovado", columnList = "aprovado")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freteiro_id", nullable = false)
    private Freteiro freteiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "nome_avaliador", length = 100)
    private String nomeAvaliador;

    @Column(nullable = false)
    private Integer nota; // 1-5

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "resposta_freteiro", columnDefinition = "TEXT")
    private String respostaFreteiro;

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta;

    @Column(nullable = false)
    private Boolean aprovado = false;

    @Column(nullable = false)
    private Boolean denunciado = false;

    @Column(name = "motivo_denuncia", columnDefinition = "TEXT")
    private String motivoDenuncia;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
