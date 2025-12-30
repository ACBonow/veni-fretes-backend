package com.venifretes.dto.response;

import com.venifretes.model.enums.TipoVeiculo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class FreteiroListResponse {
    private Long id;
    private String nome;
    private String slug;
    private String telefone;
    private String cidade;
    private String estado;
    private String fotoPerfil;
    private BigDecimal avaliacaoMedia;
    private Integer totalAvaliacoes;
    private List<TipoVeiculo> tiposVeiculo;
    private Boolean verificado;
}
