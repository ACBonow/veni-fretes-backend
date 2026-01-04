package com.venifretes.dto.request;

import jakarta.validation.constraints.*;
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
public class PlanoRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String nome;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal preco;

    private Map<String, Object> features;

    @NotNull(message = "Posição no ranking é obrigatória")
    @Min(value = 1, message = "Posição no ranking deve ser no mínimo 1")
    private Integer posicaoRanking;

    @NotNull(message = "Limite de fotos é obrigatório")
    @Min(value = 0, message = "Limite de fotos deve ser no mínimo 0")
    private Integer limiteFotos;

    @NotNull(message = "Ordem é obrigatória")
    @Min(value = 1, message = "Ordem deve ser no mínimo 1")
    private Integer ordem;

    private Boolean ativo = true;
}
