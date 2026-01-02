package com.venifretes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Métricas de crescimento da plataforma")
public class GrowthStatsResponse {

    @Schema(description = "Cadastros nos últimos 7 dias", example = "45")
    private Long cadastrosUltimos7Dias;

    @Schema(description = "Cadastros nos últimos 30 dias", example = "182")
    private Long cadastrosUltimos30Dias;

    @Schema(description = "Cadastros nos últimos 90 dias", example = "534")
    private Long cadastrosUltimos90Dias;

    @Schema(description = "Cadastros por dia (últimos 30 dias)")
    private List<DailyCounts> cadastrosPorDia;

    @Schema(description = "Taxa de crescimento mensal (%)", example = "12.5")
    private Double taxaCrescimentoMensal;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Contagem diária de cadastros")
    public static class DailyCounts {

        @Schema(description = "Data", example = "2026-01-01")
        private LocalDate data;

        @Schema(description = "Quantidade de cadastros", example = "8")
        private Long quantidade;
    }
}
