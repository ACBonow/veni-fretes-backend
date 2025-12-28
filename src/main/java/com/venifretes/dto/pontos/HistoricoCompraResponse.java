package com.venifretes.dto.pontos;

import com.venifretes.model.enums.MetodoPagamento;
import com.venifretes.model.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoCompraResponse {

    private Long id;
    private String transacaoId;
    private String cidade;
    private Integer quantidadePontos;
    private BigDecimal valorTotal;
    private BigDecimal descontoPercentual;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento status;
    private Integer posicaoEstimadaAntes;
    private Integer posicaoEstimadaDepois;
    private LocalDateTime dataPagamento;
    private LocalDateTime dataExpiracao;
    private LocalDateTime createdAt;
}
