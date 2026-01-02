package com.venifretes.dto.response;

import com.venifretes.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta unificada para listagem de pessoas (usuários, freteiros, contratantes, admins)")
public class PessoaListResponse {

    // Campos comuns a todos
    @Schema(description = "ID da pessoa", example = "1")
    private Long id;

    @Schema(description = "Nome da pessoa", example = "João Silva")
    private String nome;

    @Schema(description = "Email", example = "joao@example.com")
    private String email;

    @Schema(description = "Telefone", example = "(11) 98765-4321")
    private String telefone;

    @Schema(description = "Role/Tipo de usuário", example = "FRETEIRO")
    private Role role;

    @Schema(description = "Se o usuário está ativo", example = "true")
    private Boolean ativo;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Último login")
    private LocalDateTime lastLoginAt;

    @Schema(description = "Se o email foi verificado", example = "true")
    private Boolean emailVerificado;

    // Campos específicos de Freteiro (opcional)
    @Schema(description = "Slug do freteiro (apenas para FRETEIRO)", example = "joao-silva-sp")
    private String slug;

    @Schema(description = "Cidade (apenas para FRETEIRO)", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado (apenas para FRETEIRO)", example = "SP")
    private String estado;

    @Schema(description = "Se o freteiro foi verificado (apenas para FRETEIRO)", example = "true")
    private Boolean verificado;

    @Schema(description = "Avaliação média do freteiro (apenas para FRETEIRO)", example = "4.5")
    private BigDecimal avaliacaoMedia;

    // Campos específicos de Contratante (opcional)
    @Schema(description = "CPF/CNPJ (apenas para CONTRATANTE)", example = "123.456.789-00")
    private String cpfCnpj;

    // Campos específicos de Admin (opcional)
    @Schema(description = "Se é super admin (apenas para ADMIN)", example = "false")
    private Boolean superAdmin;
}
