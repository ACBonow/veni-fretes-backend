package com.venifretes.dto.pontos;

import com.venifretes.model.enums.MetodoPagamento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraPontosRequest {

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotNull(message = "Quantidade de pontos é obrigatória")
    @Min(value = 10, message = "Quantidade mínima de pontos é 10")
    private Integer quantidadePontos;

    @NotNull(message = "Método de pagamento é obrigatório")
    private MetodoPagamento metodoPagamento;
}
