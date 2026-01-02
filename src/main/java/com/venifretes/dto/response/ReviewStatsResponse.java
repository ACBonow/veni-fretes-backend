package com.venifretes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estatísticas de avaliações")
public class ReviewStatsResponse {

    @Schema(description = "Total de avaliações", example = "456")
    private Long totalAvaliacoes;

    @Schema(description = "Avaliações aprovadas", example = "398")
    private Long avaliacoesAprovadas;

    @Schema(description = "Avaliações pendentes", example = "58")
    private Long avaliacoesPendentes;

    @Schema(description = "Nota média geral", example = "4.3")
    private BigDecimal notaMediaGeral;

    @Schema(description = "Distribuição de avaliações por nota (1-5 estrelas)")
    private Map<Integer, Long> distribuicaoPorNota;
}
