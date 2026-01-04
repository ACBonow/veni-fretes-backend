package com.venifretes.model.entity;

import com.venifretes.model.enums.TipoConfiguracao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "configuracoes_sistema")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracaoSistema {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "chave", nullable = false, unique = true)
    private TipoConfiguracao chave;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String valor;

    @Column(length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "tipo_dado", length = 20)
    private String tipoDado;

    @Column(name = "valor_padrao", columnDefinition = "TEXT")
    private String valorPadrao;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}
