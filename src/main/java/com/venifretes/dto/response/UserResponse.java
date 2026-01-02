package com.venifretes.dto.response;

import com.venifretes.model.enums.Role;
import com.venifretes.model.enums.TipoServico;
import com.venifretes.model.enums.TipoVeiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Role role;
    private Boolean emailVerificado;
    private Boolean ativo;
    private LocalDateTime createdAt;

    // Campos específicos de Freteiro (opcionais)
    private String slug;
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

    // Métricas de tracking
    private Long totalVisualizacoes;
    private Long totalCliques;
    private Long cliquesWhatsApp;
    private Long cliquesTelefone;
}
