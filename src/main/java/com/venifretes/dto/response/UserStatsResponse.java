package com.venifretes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estatísticas de usuários do sistema")
public class UserStatsResponse {

    @Schema(description = "Total de usuários cadastrados", example = "1250")
    private Long totalUsuarios;

    @Schema(description = "Total de freteiros", example = "850")
    private Long totalFreteiros;

    @Schema(description = "Total de contratantes", example = "380")
    private Long totalContratantes;

    @Schema(description = "Total de administradores", example = "5")
    private Long totalAdmins;

    @Schema(description = "Usuários ativos", example = "1100")
    private Long usuariosAtivos;

    @Schema(description = "Usuários inativos", example = "150")
    private Long usuariosInativos;

    @Schema(description = "Freteiros verificados", example = "320")
    private Long freteirosVerificados;

    @Schema(description = "Freteiros ativos", example = "750")
    private Long freteirosAtivos;
}
