package com.venifretes.model.entity;

import com.venifretes.model.enums.TipoEvento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos_tracking", indexes = {
    @Index(name = "idx_evento_freteiro", columnList = "freteiro_id"),
    @Index(name = "idx_evento_tipo", columnList = "tipo"),
    @Index(name = "idx_evento_created", columnList = "created_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freteiro_id", nullable = false)
    private Freteiro freteiro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoEvento tipo;

    @Column(name = "ip", length = 45)
    private String ip;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(length = 100)
    private String origem;

    @Column(columnDefinition = "TEXT")
    private String referer;

    @Column(name = "notificacao_enviada")
    private Boolean notificacaoEnviada = false;

    @Column(name = "data_notificacao")
    private LocalDateTime dataNotificacao;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
