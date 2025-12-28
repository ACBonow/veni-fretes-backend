package com.venifretes.model.entity;

import com.venifretes.model.enums.StatusAssinatura;
import com.venifretes.model.enums.TipoServico;
import com.venifretes.model.enums.TipoVeiculo;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "freteiros", indexes = {
    @Index(name = "idx_freteiro_slug", columnList = "slug", unique = true),
    @Index(name = "idx_freteiro_cidade_estado", columnList = "cidade,estado"),
    @Index(name = "idx_freteiro_verificado", columnList = "verificado")
})
@PrimaryKeyJoinColumn(name = "usuario_id")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Freteiro extends Usuario {

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Type(JsonType.class)
    @Column(name = "areas_atendidas", columnDefinition = "jsonb")
    private List<String> areasAtendidas = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @Type(JsonType.class)
    @Column(name = "fotos_veiculo", columnDefinition = "jsonb")
    private List<String> fotosVeiculo = new ArrayList<>();

    @ElementCollection(targetClass = TipoVeiculo.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "freteiro_tipos_veiculo",
                     joinColumns = @JoinColumn(name = "freteiro_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_veiculo")
    private List<TipoVeiculo> tiposVeiculo = new ArrayList<>();

    @ElementCollection(targetClass = TipoServico.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "freteiro_tipos_servico",
                     joinColumns = @JoinColumn(name = "freteiro_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servico")
    private List<TipoServico> tiposServico = new ArrayList<>();

    @Column(name = "porcentagem_completude")
    private Integer porcentagemCompletude = 0;

    @Column(name = "avaliacao_media", precision = 3, scale = 2)
    private BigDecimal avaliacaoMedia = BigDecimal.ZERO;

    @Column(name = "total_avaliacoes")
    private Integer totalAvaliacoes = 0;

    @Column(name = "total_visualizacoes")
    private Long totalVisualizacoes = 0L;

    @Column(name = "total_cliques")
    private Long totalCliques = 0L;

    @Column(name = "cliques_whatsapp")
    private Long cliquesWhatsApp = 0L;

    @Column(name = "cliques_telefone")
    private Long cliquesTelefone = 0L;

    @Column(name = "verificado")
    private Boolean verificado = false;

    @Column(name = "data_verificacao")
    private LocalDateTime dataVerificacao;

    @OneToMany(mappedBy = "freteiro", cascade = CascadeType.ALL)
    private List<Assinatura> assinaturas = new ArrayList<>();

    @OneToMany(mappedBy = "freteiro", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    // Helper method para assinatura ativa
    public Assinatura getAssinaturaAtiva() {
        return assinaturas.stream()
            .filter(a -> a.getStatus() == StatusAssinatura.ATIVA ||
                        a.getStatus() == StatusAssinatura.EM_PERIODO_TESTE)
            .findFirst()
            .orElse(null);
    }
}
