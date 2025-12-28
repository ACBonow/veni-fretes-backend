package com.venifretes.dto.filter;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FreteiroFilterDTO {
    private String cidade;
    private String estado;
    private BigDecimal avaliacaoMinima;
    private String busca;
}
