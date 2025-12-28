package com.venifretes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    // Métricas de visualização
    private Long totalVisualizacoes;
    private Long visualizacoesUltimos30Dias;

    // Métricas de cliques
    private Long totalCliques;
    private Long cliquesWhatsApp;
    private Long cliquesTelefone;
    private Long cliquesUltimos30Dias;

    // Métricas de avaliações
    private BigDecimal avaliacaoMedia;
    private Integer totalAvaliacoes;

    // Completude do perfil
    private Integer porcentagemCompletude;

    // Status
    private Boolean verificado;
    private Boolean ativo;

    // Taxa de conversão
    private Double taxaConversao; // cliques / visualizações * 100
}
