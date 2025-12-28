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
public class CompraPontosResponse {

    private Long id;
    private String transacaoId;
    private Integer quantidadePontos;
    private BigDecimal valorTotal;
    private BigDecimal descontoPercentual;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento status;
    private String qrCodePix;
    private String linkPagamento;
    private Integer posicaoEstimada;
    private Integer diasValidade;
    private LocalDateTime expiraEm;
    private LocalDateTime createdAt;
    private String mensagem;
}
