package com.venifretes.dto.pontos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PontosStatusResponse {

    private String cidade;
    private Integer pontosAtivos;
    private Integer pontosGastos;
    private Integer totalPontosComprados;
    private LocalDateTime expiraEm;
    private LocalDateTime ultimaCompra;
    private Integer diasRestantes;
    private List<HistoricoCompraResponse> ultimasCompras;
}
