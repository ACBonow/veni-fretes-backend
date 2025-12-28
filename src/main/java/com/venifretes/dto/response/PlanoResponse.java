package com.venifretes.dto.response;

import com.venifretes.model.enums.PlanoTipo;
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
public class PlanoResponse {
    private PlanoTipo id;
    private String nome;
    private BigDecimal preco;
    private Map<String, Object> features;
    private Integer posicaoRanking;
    private Integer limiteFotos;
    private Integer ordem;
}
