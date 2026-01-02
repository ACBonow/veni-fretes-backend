package com.venifretes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Métricas de engajamento da plataforma")
public class EngagementStatsResponse {

    @Schema(description = "Total de visualizações de perfis", example = "15430")
    private Long totalVisualizacoes;

    @Schema(description = "Visualizações nos últimos 30 dias", example = "3250")
    private Long visualizacoesUltimos30Dias;

    @Schema(description = "Total de cliques em contatos", example = "4820")
    private Long totalCliques;

    @Schema(description = "Cliques nos últimos 30 dias", example = "980")
    private Long cliquesUltimos30Dias;

    @Schema(description = "Cliques no botão WhatsApp", example = "3100")
    private Long cliquesWhatsApp;

    @Schema(description = "Cliques no botão Telefone", example = "1720")
    private Long cliquesTelefone;

    @Schema(description = "Taxa de conversão geral (cliques/visualizações)", example = "0.312")
    private Double taxaConversaoGeral;
}
