package com.venifretes.dto.response;

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
public class AvaliacaoStatsResponse {
    private BigDecimal mediaGeral;
    private Long totalAvaliacoes;
    private Map<Integer, Long> distribuicaoPorNota;
}
