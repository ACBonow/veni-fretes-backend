package com.venifretes.dto.pontos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimulacaoPontosResponse {

    private Integer posicaoAtual;
    private Integer posicaoComPontos;
    private Integer pontosAdicionais;
    private Integer pontosNecessariosProximaPosicao;
    private BigDecimal valorPorPonto;
    private BigDecimal valorTotal;
    private BigDecimal descontoPercentual;
    private Integer diasValidade;
    private String mensagem;
    private List<ConcorrenteInfo> concorrentesNaFrente;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ConcorrenteInfo {
        private String nome;
        private String plano;
        private Integer pontos;
        private Integer posicao;
    }
}
