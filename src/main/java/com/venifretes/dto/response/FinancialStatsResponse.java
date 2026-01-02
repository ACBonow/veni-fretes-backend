package com.venifretes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Métricas financeiras da plataforma")
public class FinancialStatsResponse {

    @Schema(description = "Receita total de assinaturas", example = "12500.00")
    private BigDecimal receitaTotalAssinaturas;

    @Schema(description = "Receita dos últimos 30 dias", example = "3450.00")
    private BigDecimal receitaUltimos30Dias;

    @Schema(description = "Quantidade de assinaturas ativas", example = "125")
    private Long assinaturasAtivas;

    @Schema(description = "Receita total de vendas de pontos", example = "5680.00")
    private BigDecimal receitaPontos;

    @Schema(description = "Total de pontos vendidos", example = "15420")
    private Integer totalPontosVendidos;
}
