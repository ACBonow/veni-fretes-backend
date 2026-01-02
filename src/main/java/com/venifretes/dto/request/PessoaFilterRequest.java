package com.venifretes.dto.request;

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
@Schema(description = "Filtros para busca de pessoas")
public class PessoaFilterRequest {

    @Schema(description = "Buscar por nome (contém, case-insensitive)", example = "João")
    private String nome;

    @Schema(description = "Filtrar por email (contém, case-insensitive)", example = "joao@")
    private String email;

    @Schema(description = "Filtrar por role", example = "FRETEIRO")
    private Role role;

    @Schema(description = "Filtrar por status ativo/inativo", example = "true")
    private Boolean ativo;

    @Schema(description = "Filtrar por cidade (apenas para freteiros)", example = "São Paulo")
    private String cidade;

    @Schema(description = "Filtrar por verificação (apenas para freteiros)", example = "true")
    private Boolean verificado;

    @Schema(description = "Cadastros criados após esta data")
    private LocalDateTime dataInicio;

    @Schema(description = "Cadastros criados antes desta data")
    private LocalDateTime dataFim;
}
