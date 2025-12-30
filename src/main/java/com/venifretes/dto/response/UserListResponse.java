package com.venifretes.dto.response;

import com.venifretes.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com dados do usuário para listagem")
public class UserListResponse {

    @Schema(description = "ID do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo", example = "João Silva")
    private String nome;

    @Schema(description = "Email", example = "joao@example.com")
    private String email;

    @Schema(description = "Telefone", example = "53999999999")
    private String telefone;

    @Schema(description = "Role do usuário", example = "FRETEIRO")
    private Role role;

    @Schema(description = "Email verificado", example = "true")
    private Boolean emailVerificado;

    @Schema(description = "Conta ativa", example = "true")
    private Boolean ativo;

    @Schema(description = "Data de criação")
    private LocalDateTime createdAt;

    @Schema(description = "Último login")
    private LocalDateTime lastLoginAt;
}
