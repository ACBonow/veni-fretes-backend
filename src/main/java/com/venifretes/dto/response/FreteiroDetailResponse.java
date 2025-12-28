package com.venifretes.dto.response;

import com.venifretes.model.enums.TipoServico;
import com.venifretes.model.enums.TipoVeiculo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class FreteiroDetailResponse {
    private Long id;
    private String nome;
    private String slug;
    private String telefone;
    private String email;
    private String cidade;
    private String estado;
    private List<String> areasAtendidas;
    private String descricao;
    private String fotoPerfil;
    private List<String> fotosVeiculo;
    private List<TipoVeiculo> tiposVeiculo;
    private List<TipoServico> tiposServico;
    private Integer porcentagemCompletude;
    private BigDecimal avaliacaoMedia;
    private Integer totalAvaliacoes;
    private Boolean verificado;
}
